package rule_utilities.stat_utilities.conditions;

import rule_utilities.CryptoRule;
import rule_utilities.stat_utilities.Stat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryCondition implements RuleCondition {
    String op = "", firstOperandName, secondOperandName;
    CryptoRule parentRule;


    public BinaryCondition(CryptoRule parent) {
        parentRule = parent;
    }

    @Override
    public RuleCondition configureCondition(String stringCondition) throws Exception {
        Matcher matcher = Pattern.compile("^(?<one>\\w+)(\\s+)?(?<op>[><=]+)(\\s+)?(?<two>\\w+)$").matcher(stringCondition);
        if (matcher.matches()) {
            op = matcher.group("op");
            firstOperandName = matcher.group("one");
            secondOperandName = matcher.group("two");

        } else
            throw new Exception("Malformed Condition String");
        return this;
    }

    @Override
    public boolean isConditionMet() {
        Stat firstOperand = parentRule.getStatByName(firstOperandName);
        Stat secondOperand = parentRule.getStatByName(secondOperandName);
        return evaluate(firstOperand, secondOperand);
    }

    private boolean evaluate(Stat firstOperand, Stat secondOperand) {
        if (!(firstOperand.isReady() || secondOperand.isReady()))
            return false;
        switch (op) {
            case "=":
                return firstOperand.getValue() == secondOperand.getValue();
            case ">":
                return firstOperand.getValue() > secondOperand.getValue();
            case "<":
                return firstOperand.getValue() < secondOperand.getValue();
            case ">=":
                return firstOperand.getValue() >= secondOperand.getValue();
            case "<=":
                return firstOperand.getValue() <= secondOperand.getValue();
        }
        return false;
    }
}
