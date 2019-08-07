### docker file of customized ubuntu
```
FROM ubuntu

RUN apt-get update \
  && apt-get install -y python3-pip python3-dev automake libtool make gcc git libjansson-dev libmagic-dev \
  && cd /usr/local/bin \
  && ln -s /usr/bin/python3 python \
  && pip3 install --upgrade pip

RUN mkdir /app
WORKDIR /app
ENV LC_ALL=C.UTF-8

# Install yara-python and ssdeep
RUN pip install yara-python
RUN apt-get install -y build-essential libffi-dev python3 python3-dev python3-pip libfuzzy-dev
RUN pip install ssdeep

CMD python
```


### docker build and run
```sh
# build the docker image from Dockerfile
docker build --tag <image name> .

# run the containter in interative mode
docker run -ti <image name> /bin/bash
```


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
