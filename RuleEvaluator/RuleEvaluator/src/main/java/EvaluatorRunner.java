import consume_procedures.ConsumeProcedure;
import consume_procedures.DataRepoProcedure;
import database_utilities.DatabaseConnection;
import kafka_utilities.CandleConsumer;

import properties_reader.PropertiesReader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;

public class EvaluatorRunner {
    public void run() throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader();
        int port = Integer.parseInt(propertiesReader.readFromConfig("kafka_port", "rule_eval_config"));
        String host = propertiesReader.readFromConfig("kafka_host", "rule_eval_config");
        String[] marketNames = propertiesReader.readFromConfig("markets", "rule_eval_config")
                .replaceAll(" +", "")
                .split(",");
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.getConnection();
        for (String marketName : marketNames) {
            CandleConsumer candleConsumer = new CandleConsumer(InetAddress.getLocalHost().getHostName()
                    ,host
                    , marketName + "ConsumerGroup"
                    , port,
                    marketName);
            ConsumeProcedure procedure = new DataRepoProcedure()
                    .introduceConsumer(candleConsumer, marketName)
                    .introduceDatabaseConnection(dbConnection);
            new Thread(procedure).start();
        }
    }
}
