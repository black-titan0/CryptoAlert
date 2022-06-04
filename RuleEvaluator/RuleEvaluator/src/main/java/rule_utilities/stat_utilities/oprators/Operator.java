package rule_utilities.stat_utilities.oprators;

import rule_utilities.stat_utilities.Stat;

public interface Operator {

    static Operator getOperator(OperatorType operatorType) throws Exception {
        switch (operatorType){
            case MOVING_AVERAGE:
                return new FixedSizeQueueMean();
        }
        throw new Exception("Unidentified Operator");
    }

    public double calculate(Stat stat);
}
