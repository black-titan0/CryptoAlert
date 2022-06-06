package consume_procedures;

import database_utilities.DatabaseConnection;
import kafka_utilities.CandleConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;


import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class PrintProcedure implements ConsumeProcedure{

    private CandleConsumer consumer;
    private String target;
    private boolean running = true;
    private DatabaseConnection connection;

    @Override
    public ConsumeProcedure introduceConsumer(CandleConsumer consumer, String targetMarket) {
        this.consumer = consumer;
        target = targetMarket;
        return this;
    }

    @Override
    public ConsumeProcedure introduceDatabaseConnection(DatabaseConnection connection) {
        this.connection = connection;
        return this;
    }

    @Override
    public void run() {
        consumer.subscribeToTopic();
        while (running) {
            List<ConsumerRecord> records = consumer.pollRecords(Duration.ofMillis(1000));
            for (ConsumerRecord record : records) {
                System.out.println("Key: " + record.key() + ", Value: " + record.value());
                System.out.println("Partition: " + record.partition() + ", Offset: " + record.offset());
            }
            consumer.sync();
        }
    }

    @Override
    public void stop() {
        running = false;
    }
}
