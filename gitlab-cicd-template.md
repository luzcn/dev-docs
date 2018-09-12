stages:
  - build
  - docker
  - deployStaging

cache:
  key: "$CI_JOB_REF_NAME"
  paths:
    - .m2/repository

build:
  image: maven:3-jdk-10-slim
  stage: build
  script:
    - mvn package
  artifacts:
    paths:
      - $CI_PROJECT_DIR/target/*.war

docker:
  image: docker:stable
  stage: docker
  services:
    - docker:dind
  tags:
    - docker
  only:
    - /^master.*/
  script:
    - docker build . -t ${CI_REGISTRY_IMAGE}:${CI_PIPELINE_ID}
    - docker login $CI_REGISTRY -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD
    - docker push ${CI_REGISTRY_IMAGE}:${CI_PIPELINE_ID}

deployStaging:
  image: zappi/kubectl:1.8.13
  stage: deployStaging
  environment:
    name: staging
  variables:
    KUBECONFIG: /kubeconfig.yml
    KUBERNETES_CLUSTER: aws-nonprod-cluster-1
    KUBECTL_BEARER_TOKEN: $KUBERNETES_NONPROD_BEARER_TOKEN
    DEPLOYMENT_NAME: wm-event-receiver
  only:
    - /^master.*/
  before_script:
    - wget -qO $KUBECONFIG https://s3-us-west-2.amazonaws.com/nord-cicd-public/kubeconfig.yml
    - kubectl config set-credentials $KUBERNETES_CLUSTER --token=$KUBECTL_BEARER_TOKEN
    - kubectl config use-context $KUBERNETES_CLUSTER
  script:
    - CI_REGISTRY_IMAGE_ESCAPED=$(echo ${CI_REGISTRY_IMAGE} | sed -e "s#/#\\\/#g")
    - cat k8s/deployment.yml | sed "s/CI_REGISTRY_IMAGE/$CI_REGISTRY_IMAGE_ESCAPED/g" | sed "s/CI_PIPELINE_ID/$CI_PIPELINE_ID/g" > k8s/deployment-temp.yml
    - kubectl -n wine apply -f k8s/deployment-temp.yml
    - kubectl -n wine rollout status deployments/$DEPLOYMENT_NAME -w
  tags:
    - default
