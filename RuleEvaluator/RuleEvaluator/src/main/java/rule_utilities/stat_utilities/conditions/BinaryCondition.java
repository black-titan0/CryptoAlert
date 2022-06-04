package rule_utilities.stat_utilities.conditions;

import rule_utilities.CryptoRule;
import rule_utilities.stat_utilities.Stat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryCondition implements RuleCondition{
    String op = "", firstOperandName, secondOperandName;
    CryptoRule parentRule;


    public BinaryCondition(CryptoRule parent){
        parentRule = parent;
    }
    @Override
    public RuleCondition configureCondition(String stringCondition) throws Exception {
        Matcher matcher = Pattern.compile("^(?<value1>\\w+)(\\s+)?(?<op>[><=]{1,2})(\\s+)?(?<value2>\\w+)$").matcher(stringCondition);
        if (matcher.matches()){
            op = matcher.group("op");
            firstOperandName = matcher.group("value1");
            secondOperandName = matcher.group("value2");

        }
        throw new Exception("Malformed Condition String");
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
        switch (op){
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
