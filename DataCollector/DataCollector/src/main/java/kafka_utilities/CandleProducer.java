package kafka_utilities;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class CandleProducer {

    KafkaProducer<Object, String> producer;
    public CandleProducer(String clientId, String host, int port)  {
        Properties config = new Properties();
        config.put("client.id", clientId);
        config.put("bootstrap.servers", host + ":" + port);
        config.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(config);
    }

    public void publishCandle(String topic,String candleData){
        producer.send(new ProducerRecord<>(topic, candleData));
    }

    public void publishMultipleCandles(String topic, List<String> candleData){
        for (String datum : candleData) {
            publishCandle(topic, datum);
            LoggerFactory.getLogger(this.getClass()).info("\n new candle incoming : \n" + datum);
        }
    }
}
