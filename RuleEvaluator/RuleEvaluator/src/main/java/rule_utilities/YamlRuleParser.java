package rule_utilities;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YamlRuleParser implements RuleParser{
    @Override
    public Set<String>  getRuleNames(Object rules) {
        Map<String, Object> yamlRuleMap = ((Map<String, Object> ) rules);
        Set<String> keys = yamlRuleMap.keySet();
        return keys;
    }

    @Override
    public Object adjustTimeScales(Object rule) throws Exception {
        Map<String, Object> yamlRuleMap = ((Map<String, Object> ) rule);
        for (String statName : yamlRuleMap.keySet()) {
            if (!statName.startsWith("stat"))
                continue;
            Map<String, Object> yamlStatMap = (Map<String, Object>) yamlRuleMap.get(statName);
            String timeString = (String) yamlStatMap.get("time");
            Matcher matcher = Pattern.compile("^(?<amount>\\d+)(?<timeScale>[M|w|d|h|m])$").matcher(timeString);
            if (matcher.matches()) {
                String timeScale = matcher.group("timeScale");
                int amount = Integer.parseInt(matcher.group("amount"));
                amount = changeTimeScale(amount, timeScale);
                yamlStatMap.put("time", amount);
                yamlRuleMap.put(statName, yamlStatMap);
            } else throw new Exception("Malformed Yaml Rule time : " + timeString);;
        }
        return yamlRuleMap;
    }

    private int changeTimeScale(int amount, String timeScale) {
        int coef = 1;
        switch (timeScale){
            case "M":
                coef = 43200; // every month is 43200 minutes
                break;
            case "w":
                coef = 10080; // every week is 10080 minutes
                break;
            case "d":
                coef = 1440; // every day is 1440 minutes
                break;
            case "h":
                coef = 60; // every hour is 60 minutes
                break;
        }
        return coef * amount;
    }

    @Override
    public String getReferencePrice(Object rule) {
        return null;
    }

    @Override
    public Object getRuleByName(Object rules, String ruleName) {
        Map<String, Object> yamlRuleMap = ((Map<String, Object> ) rules);
        return yamlRuleMap.get(ruleName);
    }
}
