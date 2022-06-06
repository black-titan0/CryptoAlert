package consume_procedures;

import alerts.Alert;
import com.binance.api.client.domain.market.Candlestick;
import com.google.gson.Gson;
import database_utilities.DatabaseConnection;
import kafka_utilities.CandleConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import rule_utilities.*;
import rule_utilities.read_parse.RuleParser;
import rule_utilities.read_parse.RuleReader;
import rule_utilities.read_parse.YamlRuleParser;
import rule_utilities.read_parse.YamlRuleReader;

import java.sql.Connection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataRepoProcedure implements ConsumeProcedure {

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
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        LoggerFactory.getLogger(this.getClass()).info("New DataRepoProcedure Started For Market " + target);
        Gson gson = new Gson();
        RuleReader ruleReader = new YamlRuleReader();
        RuleParser ruleParser = new YamlRuleParser();
        ArrayList<CryptoRule> rules = new ArrayList<>();
        Map<String, Object> ruleMaps = (Map<String, Object>) ruleReader.readRules(target);

        for (String ruleName : ruleParser.getRuleNames(ruleMaps)) {
            Map<String, Object> rule = (Map<String, Object>) ruleParser.getRuleByName(ruleMaps, ruleName);
            try {
                Map<String, Object> adjustedRule = (Map<String, Object>) ruleParser.adjustTimeScales(rule);
                CryptoRule cryptoRule = new CryptoRule(adjustedRule, ruleName, target);
                rules.add(cryptoRule);
                consumer.subscribeToTopic();
                while (running) {
                    List<ConsumerRecord> records = consumer.pollRecords(Duration.ofMillis(1000));
                    for (ConsumerRecord record : records) {
                        Candlestick candlestick = gson.fromJson(record.value().toString(), Candlestick.class);
                        for (CryptoRule cr:rules) {
                            cr.considerNewRecord(candlestick);
                            var result = cr.evaluateRule();
                            if (result instanceof Alert)
                                connection.insertAlert((Alert) result);
                        }
                    }
                    consumer.sync();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
