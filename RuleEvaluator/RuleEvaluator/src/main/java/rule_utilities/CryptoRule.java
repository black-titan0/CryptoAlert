package rule_utilities;

import com.binance.api.client.domain.market.Candlestick;
import stat_utilities.FixedSizeQueueStat;
import stat_utilities.Stat;
import stat_utilities.oprators.OperatorType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptoRule {

    private HashMap<String, Stat> stats = new HashMap<>();
    public CryptoRule(Map<String, Object> ruleMap) {
        for (String statName : ruleMap.keySet()) {
            if (statName.startsWith("cond")) {

                continue;
            }
            Map<String, Object> statMap = (Map<String, Object>) ruleMap.get(statName);
            int time = (int) statMap.get("time");
            String  operatorTypeString = (String) statMap.get("time");
            Stat newStat = new FixedSizeQueueStat(time, OperatorType.fromString(operatorTypeString));
            stats.put(statName, newStat);
        }
    }

    public void considerNewRecord(Candlestick newCandle) {
        for (Stat stat:stats.values()) {
            stat.addCandleRecord(newCandle);
        }
    }
}
