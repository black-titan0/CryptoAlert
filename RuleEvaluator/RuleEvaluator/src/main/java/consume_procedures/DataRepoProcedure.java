package consume_procedures;

import kafka_utilities.CandleConsumer;
import rule_utilities.RuleParser;
import rule_utilities.RuleReader;
import rule_utilities.YamlRuleParser;
import rule_utilities.YamlRuleReader;

import java.util.Map;

public class DataRepoProcedure implements ConsumeProcedure {

    private CandleConsumer consumer;
    private String target;
    @Override
    public ConsumeProcedure introduceConsumer(CandleConsumer consumer,String targetMarket) {
        this.consumer = consumer;
        target = targetMarket;
        return this;
    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        System.out.println("New DataRepoProcedure Started For Market " + target);
        RuleReader ruleReader = new YamlRuleReader();
        RuleParser ruleParser = new YamlRuleParser();
        Map<String, Object> rules = (Map<String, Object>) ruleReader.readRules(target);
        for (String ruleName: ruleParser.getRuleNames(rules)) {
            Map<String, Object> rule = (Map<String, Object>) ruleParser.getRuleByName(rules ,ruleName);
            try {
                Map<String, Object> adjustedRule = (Map<String, Object>) ruleParser.adjustTimeScales(rule);
                System.out.println(adjustedRule);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
