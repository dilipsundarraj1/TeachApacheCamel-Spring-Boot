# Kafka Commands

## How to download Kafka ?

**Approach 1**

Step 1:   

https://kafka.apache.org/downloads

Step 2:  

Modify the **server.properties** file.

```
advertised.listeners=PLAINTEXT://localhost:9092
```

**Approach 2**
```
curl "http://mirror.metrocast.net/apache/kafka/0.10.2.0/kafka_2.12-0.10.2.0.tgz" | tar xz
```

## How to start a zookeeper ?

**Windows:**

```
zookeeper-server-start.bat ..\..\config\zookeeper.properties
```

**MAC/Unix:**

```
./zookeeper-server-start.sh ../config/zookeeper.properties
```

## How to start a Kafka Broker ?

**Windows:**

```
kafka-server-start.bat ..\..\config\server.properties
```

**MAC/Unix :**

```
./kafka-server-start.sh ../config/server.properties
```
## How to create a topic ?

**Windows**

```
kafka-topics.bat --create --topic inputItemTopic -zookeeper localhost:2181 --replication-factor 1 --partitions 3.

kafka-topics.bat --create --topic errorTopic -zookeeper localhost:2181 --replication-factor 1 --partitions 3.
```


**MAC:**  

```
./kafka-topics.sh --create --topic inputItemTopic -zookeeper localhost:2181 --replication-factor 1 --partitions 3

./kafka-topics.sh --create --topic errorTopic -zookeeper localhost:2181 --replication-factor 1 --partitions 3

```
## How to check the configuration of all the topics in a broker ?
**Windows**

```
kafka-topics.bat --describe --zookeeper localhost:2181
```

**MAC:**
```
./kafka-topics.sh --describe --zookeeper localhost:2181
```

## How to check the configuration of a particular topic?
**Windows**

```
kafka-topics.bat --describe --topic inputItemTopic --zookeeper localhost:2181

kafka-topics.bat --describe --topic errorTopic --zookeeper localhost:2181
```
**MAC:**  
```
./kafka-topics.sh --describe --topic inputItemTopic --zookeeper localhost:2181

./kafka-topics.sh --describe --topic errorTopic --zookeeper localhost:2181
```

## How to instantiate a Console Producer?

**Windows:**

```
kafka-console-producer.bat --broker-list localhost:9092 --topic inputItemTopic
kafka-console-producer.bat --broker-list localhost:9092 --topic errorTopic
```

**MAC:**  

```
./kafka-console-producer.sh --broker-list localhost:9092 --topic inputItemTopic
./kafka-console-producer.sh --broker-list localhost:9092 --topic errorTopic
```

## How to publish multiple messages using a console producer?


**MAC**

```
./kafka-consumer-perf-test.sh --topic inputItemTopic
```

## How to instantiate a Console Consumer?

```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic inputItemTopic --from-beginning
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic errorTopic --from-beginning

```

```
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic inputItemTopic --from-beginning
./kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic errorTopic --from-beginning

```

## How to delete a topic?

**Windows**

```
kafka-topics.bat --delete --zookeeper localhost:2181 --topic your_topic_name
```

**MAC:**

```
./kafka-topics.sh --delete --zookeeper localhost:2181 --topic your_topic_name
```

## How to alter the configuration of a topic ?

**Windows:**

```
kafka-topics.bat --zookeeper localhost:2181 --alter --topic inputItemTopic --partitions 4
```

**MAC**  


```
./kafka-topics.sh --zookeeper localhost:2181 --alter --topic inputItemTopic --partitions 4
```


## How to kill the Broker Process?

Step 1:   
```
ps ax | grep -i 'kafka\.Kafka'
```

Step 2:  

```
kill -9 <processId>
```
