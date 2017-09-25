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
