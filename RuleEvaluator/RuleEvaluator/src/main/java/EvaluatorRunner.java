import consume_procedures.ConsumeProcedure;
import consume_procedures.PrintProcedure;
import kafka_utilities.CandleConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import properties_reader.PropertiesReader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

public class EvaluatorRunner {
    public void run() throws UnknownHostException {
        PropertiesReader propertiesReader = new PropertiesReader();
        int port = Integer.parseInt(propertiesReader.readFromConfig("kafka_port", "rule_eval_config"));
        String host = propertiesReader.readFromConfig("kafka_host", "rule_eval_config");
        String[] marketNames = propertiesReader.readFromConfig("markets", "rule_eval_config")
                .replaceAll(" +", "")
                .split(",");
        for (String marketName : marketNames) {
            CandleConsumer candleConsumer = new CandleConsumer(InetAddress.getLocalHost().getHostName()
                    ,host
                    , marketName + "ConsumerGroup"
                    , port,
                    marketName);
            ConsumeProcedure procedure = new PrintProcedure().introduceConsumer(candleConsumer);
            new Thread(procedure).start();
        }
    }
}
