package rule_utilities.stat_utilities.conditions;

public interface RuleCondition {
    RuleCondition configureCondition(String stringCondition) throws Exception;
    boolean isConditionMet();
}
