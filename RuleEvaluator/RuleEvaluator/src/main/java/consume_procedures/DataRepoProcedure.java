package consume_procedures;

import kafka_utilities.CandleConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class DataRepoProcedure implements ConsumeProcedure {

    private CandleConsumer consumer;
    @Override
    public ConsumeProcedure introduceConsumer(CandleConsumer consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }
}
