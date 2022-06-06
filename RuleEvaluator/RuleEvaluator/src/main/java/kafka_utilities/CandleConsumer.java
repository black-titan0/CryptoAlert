package kafka_utilities;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import javax.management.InstanceAlreadyExistsException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class CandleConsumer {

    KafkaConsumer consumer;
    String topic;
    public CandleConsumer(String clientId, String host, String group, int port, String topic)  {
        Properties config = new Properties();
        config.put("client.id", clientId + topic);
        config.put("group.id", group);
        config.put("bootstrap.servers", host + ":" + port);
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(config);
        this.topic = topic;
    }

    public CandleConsumer subscribeToTopic(){
        consumer.subscribe(Pattern.compile(topic));
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public List<ConsumerRecord> pollRecords(Duration duration) {
        ConsumerRecords records = consumer.poll(duration);
        return records.records(new TopicPartition(topic, 0));
    }

    public void sync() {
        consumer.commitSync();
    }
}
