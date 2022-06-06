package consume_procedures;

import database_utilities.DatabaseConnection;
import kafka_utilities.CandleConsumer;

import java.sql.Connection;

public interface ConsumeProcedure extends Runnable{
    ConsumeProcedure introduceConsumer(CandleConsumer consumer, String targetMarket);
    ConsumeProcedure introduceDatabaseConnection(DatabaseConnection connection);
    void stop();

}
