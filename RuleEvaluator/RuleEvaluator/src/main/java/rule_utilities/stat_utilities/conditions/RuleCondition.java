package rule_utilities.stat_utilities.conditions;

public interface RuleCondition {
    public RuleCondition configureCondition(String stringCondition) throws Exception;
    public boolean isConditionMet();
}
