package rule_utilities.read_parse;

import java.util.Set;

public interface RuleParser {
    Set<String> getRuleNames(Object rules);
    Object adjustTimeScales(Object rule) throws Exception;
    Object getRuleByName(Object rules, String ruleName);
}
