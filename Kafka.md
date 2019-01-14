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

## deprecated
kafka-topics.sh --zookeeper localhost:2181 --alter --topic test --config retention.ms=1000


### use kafak-configs

kafka-configs --zookeeper localhost:2181 --entity-type topics --alter --add-config retention.ms=1000 --entity-name <<Topic Name>>

### Configurations set via this method can be displayed with the command

kafka-configs --zookeeper localhost:2181 --entity-type topics --describe --entity-name <<Topic Name>>

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


Kafka offers several configuration that can optimize for performance, throughput, latency and reliability. Here we will go through the configuration that maximize reliability; keep in mind that this will impact negatively throughput and latency.

Topic-Level Configuration 
replication.factor=3 (or more in a case by case basis)
min.insync.replicas=2 (replication.factor - 1)
retention.ms=604800000 (3 days, it can be set to even more in a case by case basis)
unclean.leader.election.enable=false (this configuration will prevent a unclean replica to become a leader in case of broker(leader) failure, may incur unavailability)
Producer Configuration
Preventing Data Loss
acks=all
IMPORTANT: If acks is not set to all the producer is at risk of losing data in case of broker failure or rolling upgrade. This increases the latency usually to the double or triple as it requires all the min.insync.replicas to acknowledge to the leader before acknowledging back to the producer.

If your data SLA is not strict you can set acks=0 (no wait) or acks=1 (only leader acknowledgement)

Overcoming Unavailability
A partition may become unavailable in the case of broker failure, rolling upgrade or networking issues. To overcome it, Kafka Producer has a retry mechanism. We recommend to configure it to hold one second of unavailability.

retries=10
retry.backoff.ms=100 
buffer.memory should be configure to hold one second of your throughput (retries x retry.backoff.ms). By default is 32MB

Order Guarantee
max.in.flight.requests.per.connection=1 (this prevents the messages to get in disorder in case of producer retry)
Consumer Configuration
group.id={arbitrary_and_unique} (make sure your consumer group is unique so it cannot be used by any other app, also please make it self-explanatory since this shows in metrics)
enable.auto.commit=false (this will let your application decide when to commit a record, see KafkaConsumer.commitSync())
auto.commit.interval.ms=5000 (if enable.auto.commit=true, will set the pace at which the polled records are committed)
max.poll.records=1 (this will force to receive commit only one record at a time, if you use something higher you'd need to process all polled records and commit them in micro-batch)
check.crcs=true
retry.backoff.ms=100 
auto.offset.reset=none ( 'none' is recommended for non-idempotent integrations, if your application is idempotent use 'earliest')
Other important facts about Kafka Consumer
Kafka consumer (low level consumer) keeps a session to the Kafka Controller while running, to keep this session the consumer sends continuous heartbeats to the controller every heartbeat.interval.ms, if the controller does not receive a heartbeat for session.timeout.ms, it will consider the consumer as dead and will exclude it from the group. Even with this mechanism, there's a possibility that a consumer get into a vegetative state when it sends heartbeat but doesn't do any work. To handle this scenario the controller keeps track of the polling interval for the consumer, if the consumer doesn't poll within max.poll.interval.ms the controller will consider the consumer as dead. These are the default values:

heartbeat.interval.ms=3000 
session.timeout.ms=10000 
max.poll.interval.ms=300000 (5 min)
You may consider to tweak this values to your application, usually max.poll.interval.ms=60000 (1 minute) is plenty for most applications.
