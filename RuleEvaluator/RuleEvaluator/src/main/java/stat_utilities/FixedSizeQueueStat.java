package stat_utilities;

import com.binance.api.client.domain.market.Candlestick;
import stat_utilities.oprators.Operator;
import stat_utilities.oprators.OperatorType;


import java.util.ArrayList;
import java.util.List;

public class FixedSizeQueueStat implements Stat{
    List<Candlestick> candleRepo = new ArrayList<>();
    Candlestick poppedCandle;
    int objectiveDataSize, currentDataSize = 0;
    double statValue;
    Operator operator;

    public FixedSizeQueueStat(int dataSize, OperatorType operatorType) {
        operator = Operator.getOperator(operatorType);
        this.objectiveDataSize = dataSize;
    }


    public void addCandleRecord(Candlestick candlestick){

        if (isReady())
        {
            poppedCandle = candleRepo.get(0);
            candleRepo.remove(0);
        }
        candleRepo.add(candlestick);
        updateIndex();
        updateStatValue();
    }

    public void updateIndex() {
        if (currentDataSize < objectiveDataSize)
            currentDataSize++;
    }

    public boolean isReady() {
        return objectiveDataSize == currentDataSize;
    }

    public void updateStatValue() {
        statValue = operator.calculate(this);
    }
}
