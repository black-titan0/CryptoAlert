package rule_utilities;

import yaml_utilities.YamlLoader;

import java.util.Map;

public class YamlRuleReader implements RuleReader {

    @Override
    public Map<String, Object>  readRules(String marketName) {
        return new YamlLoader().getYamlData(marketName);
    }
}
