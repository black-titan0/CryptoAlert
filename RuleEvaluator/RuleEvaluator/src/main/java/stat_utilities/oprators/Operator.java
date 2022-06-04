package stat_utilities.oprators;

import stat_utilities.FixedSizeQueueStat;

public interface Operator {

    static Operator getOperator(OperatorType operatorType) {
        return null;
    }

    public float calculate(FixedSizeQueueStat stat);
}
