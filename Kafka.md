* start the zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```


* start the Kafka server
```
bin/kafka-server-start.sh config/server.properties
```

* Create topics
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

* Send some messages
```
 bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
```
