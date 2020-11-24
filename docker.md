### docker file of customized ubuntu
```
FROM ubuntu:latest

RUN apt-get update && apt-get install -y python3-pip python3 \
  build-essential libffi-dev python3-dev libfuzzy-dev libssl-dev

# link python to python3
RUN ln -s /usr/bin/python3 /usr/bin/python

# install required yara and ssdeep libs
RUN pip3 install --upgrade yara-python requests ssdeep

RUN mkdir /app
WORKDIR /app

# set localization
ENV LC_ALL=C.UTF-8

CMD python
```

### docker build and run
```sh
# build the docker image from Dockerfile
docker build --tag <image name> .

# run the containter in interative mode
docker run -ti <image name> /bin/bash

# for example 
docker run -ti --name os -v ~/project/heroku/sleuth/sleuth-codescan-agent:/app my-ubuntu /bin/bash
```

### push to docker hub
1. use `docker images` to list the docker images, get the image id that you want to push
2. `docker tag <id> luzcn/<image name>`
3. `docker login`, login with dockerhub username/password
4. `docker push`

### check what ports are already in use
```
netstat -tunlp
```

### upload local docker image to remote host docker
```
docker save grafana/grafana:latest | ssh streaming-util-02001.node.ad2.r1 'sudo docker load'
```



### run bash in a running container
```
docker exec -it <continer name> bash
```

### docker compose
```
docker-compose -f docker-compose-1kafka-1zk.yml up -d
```

### list docker containers
You can get all the containers
```
docker ps -a
```


### remove docker container
```
dokcer rm -f <container-id>
```


### list and remove dokcer images
```
docker images -a

docker rmi <image-name>

// remove all images
docker rmi $(docker images -a -q)
```

### stop and remove all docker container
```
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
```

### Get docker file from docker image
```
alias dfimage="docker run -v /var/run/docker.sock:/var/run/docker.sock --rm alpine/dfimage"
dfimage -sV=1.36 nginx:latest
```
