### Docker + Kubernetes
``` bash
kubectl config use-context docker-for-desktop

# get k8s cluster info
kubectl cluster-info

# review current context
kubectl config current-context
```

### Show Merged kubeconfig settings.
```sh
kubectl config view 

kubectl describe pod <pod name>
```

### get `stdout` log
```
kubectl logs  <pod name>
```


### use kube secret management
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: wine-rms-connector
  namespace: wine
spec:
  selector:
    matchLabels:
      app: wine-rms-connector
      environment: staging
  replicas: 1
  template:
    metadata:
      labels:
        app: wine-rms-connector
        environment: staging
    spec:
      containers:
        - name: wine-rms-connector
          image: CI_REGISTRY_IMAGE:CI_PIPELINE_ID
          ports:
          - name: http
            containerPort: 8080
            protocol: TCP
          volumeMounts:
          - name: tls-truststore
            mountPath: "/usr/src/wine/certs/"
            readOnly: true
          env:
          - name: APP_NAME
            value: wine-rms-connector
          - name: TRUSTSTORE_PASSWORD
            valueFrom:
              secretKeyRef:
                name: wine-certs
                key: kafka.truststore.password
          - name: NORD_TRUSTSTORE
            value: "/usr/src/wine/certs/kafka.truststore.jks"
          - name: AUTH_TOKEN
            valueFrom:
              secretKeyRef:
                name: wine-certs
                key: rms.auth.token
      volumes:
        - name: tls-truststore
          secret:
            secretName: wine-certs
```
