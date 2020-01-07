# Redis Cli
### Here are the commands to retrieve key value:

```
if value is of type string -> GET <key>
if value is of type hash -> HGETALL <key>
if value is of type lists -> lrange <key> <start> <end>
if value is of type sets -> smembers <key>
if value is of type sorted sets -> ZRANGEBYSCORE <key> <min> <max> or zrange <key> <min> <max>
```

### Hash

```
# add
HSET <myhash> key value

# get size
HLEN <myhash>

# get all
HGETALL <myhash>

# list all keys
HKEYS <myhash>
```

### ZSET

```
# get size
ZCOUNT myzset -inf +inf

# add
ZADD myzset 1 "one"

```

### Start/Stop Redis server

```
# Linux
/etc/init.d/redis-server stop
/etc/init.d/redis-server start

# Mac
redis-cli shutdown
```


### Redis pub/sub cli
```
# subscribe cli
subscribe <channel-name> 

publish <channel-name> <value>

# list channels
pubsub channels

```
- Redis PubSub is push mode
A client send message to channel, the Redis push messages to all subscribers.

# Redis Python Sample
- Redis pub example
```python
import redis
from datetime import datetime
import time
import random

r = redis.Redis(host='localhost', port='6379', db=0)

while True:
    r.publish('scan_results', 'pub1: ' + str(datetime.now()))
    time.sleep(random.randrange(1, 10))
```
- Redis sub example
```python
import redis
import time

r = redis.Redis(host='localhost', port='6379', db=0)
p = r.pubsub()
p.subscribe('scan_results')

while True:
        message = p.get_message()
        if message:
            print('message: ' + str(message))

        time.sleep(1)
```

