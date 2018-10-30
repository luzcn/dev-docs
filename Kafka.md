### Consumer/Producer config
```java
Consumer Config

Properties props = new Properties();

var jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
var jaasConfig = String.format(jaasTemplate, sasl_username, sasl_password);

props.put("bootstrap.servers", "kafka.nonprod.us-west-2.aws.proton.nordstrom.com:9093");
props.put("group.id", "sample-consumer");
props.put("enable.auto.commit", "true");
props.put("auto.commit.interval.ms", "1000");
props.put("session.timeout.ms", "30000");
props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("auto.offset.reset", "earliest");
props.put("security.protocol", "SASL_SSL");
props.put("sasl.mechanism", "SCRAM-SHA-512");
props.put("sasl.jaas.config", jaasConfig);


KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
consumer.subscribe(Arrays.asList(topicName));




Producer config

Properties props = new Properties();

var jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
var jaasConfig = String.format(jaasTemplate, sasl_username, sasl_password);

props.put("bootstrap.servers", "kafka.nonprod.us-west-2.aws.proton.nordstrom.com:9093");
props.put("application.id", "proton-producer-sample");

// Specify the criteria of which requests are considered complete, "all" is the slowest but most durable.
props.put(ProducerConfig.ACKS_CONFIG, "all");

// When write to topic request failed, there is no retry
props.put(ProducerConfig.RETRIES_CONFIG, "0");

// The buffer size of unsent records
props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16348);

// Producer will wait for more records to group in one batch, before sending requests
props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

props.put("security.protocol", "SASL_SSL");
props.put("sasl.mechanism", "SCRAM-SHA-512");
props.put("sasl.jaas.config", jaasConfig);

Producer<String, String> producer = new KafkaProducer<String, String>(props);


```


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
