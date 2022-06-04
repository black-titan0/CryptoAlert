package rule_utilities;

import java.util.Map;
import java.util.Set;

public interface RuleParser {
    public Set<String> getRuleNames(Object rules);

    public Object adjustTimeScales(Object rule) throws Exception;

    public String getReferencePrice(Object rule);

    Object getRuleByName(Object rules, String ruleName);
}
