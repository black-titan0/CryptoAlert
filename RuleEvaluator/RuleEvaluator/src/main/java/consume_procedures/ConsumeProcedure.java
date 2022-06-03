package consume_procedures;

import kafka_utilities.CandleConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public interface ConsumeProcedure extends Runnable{
    public ConsumeProcedure introduceConsumer(CandleConsumer consumer);
    public void stop();

}
