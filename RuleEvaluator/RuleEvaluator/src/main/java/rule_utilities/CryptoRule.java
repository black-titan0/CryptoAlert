package rule_utilities;

import alerts.Alert;
import com.binance.api.client.domain.market.Candlestick;
import rule_utilities.stat_utilities.FixedSizeQueueStat;
import rule_utilities.stat_utilities.Stat;
import rule_utilities.stat_utilities.conditions.BinaryCondition;
import rule_utilities.stat_utilities.conditions.RuleCondition;
import rule_utilities.stat_utilities.oprators.OperatorType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CryptoRule {

    private final HashMap<String, Stat> stats = new HashMap<>();
    private final String marketName;
    private RuleCondition ruleCondition;

    private String name;
    public CryptoRule(Map<String, Object> ruleMap, String ruleName, String marketName) throws Exception {
        this.marketName = marketName;
        for (String statName : ruleMap.keySet()) {
            if (statName.startsWith("cond")) {
                String condString = (String) ruleMap.get(statName);
                ruleCondition = new BinaryCondition(this).configureCondition(condString);
                continue;
            }
            Map<String, Object> statMap = (Map<String, Object>) ruleMap.get(statName);
            int time = (int) statMap.get("time");
            String  operatorTypeString = (String) statMap.get("operator");
            String  ref = (String) statMap.get("ref");
            Stat newStat = new FixedSizeQueueStat(time, OperatorType.fromString(operatorTypeString), ref);
            stats.put(statName, newStat);
            name = ruleName;
        }
    }

    public void considerNewRecord(Candlestick newCandle) {
        for (Stat stat:stats.values()) {
            stat.addCandleRecord(newCandle);
            System.out.println("new candle in " + name + " : " + newCandle.toString());
        }
    }

    public void evaluateRule() {
        if (ruleCondition.isConditionMet()){
            new Alert(LocalDateTime.now(), name, marketName);
            System.out.println("Condition Met " + name);
        }
    }

    public Stat getStatByName(String name) {
        return stats.get(name);
    }
}
