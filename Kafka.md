### start the zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```


### start the Kafka server
```
bin/kafka-server-start.sh config/server.properties
```

### Create topics
- By default, the zookeeper server has port `2181`, we can change the settings in  `config/zookeeper.properties`.
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

### Send some messages
```
 bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
```

### Start consumer
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```

### alter topic retention time
```
kafka-topics.sh --zookeeper localhost:2181 --alter --topic test --config retention.ms=1000
```
