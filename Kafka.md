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

### List the topics to which the group is subscribed
```
kafka-consumer-groups --bootstrap-server <kafkahost:port> --group <group_id> --describe
```
Note the values under "CURRENT-OFFSET" and "LOG-END-OFFSET". "CURRENT-OFFSET" is the offset where this consumer group is currently at in each of the partitions.

### Reset the consumer offset for a topic (preview)
```
kafka-consumer-groups --bootstrap-server <kafkahost:port> --group <group_id> --topic <topic_name> --reset-offsets --to-earliest
```
This will print the expected result of the reset, but not actually run it.

### Reset the consumer offset for a topic (execute)
```
kafka-consumer-groups --bootstrap-server <kafkahost:port> --group <group_id> --topic <topic_name> --reset-offsets --to-earliest --execute
```
