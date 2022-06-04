package rule_utilities.stat_utilities;

import com.binance.api.client.domain.market.Candlestick;
import rule_utilities.stat_utilities.oprators.Operator;
import rule_utilities.stat_utilities.oprators.OperatorType;


import java.util.ArrayList;
import java.util.List;

public class FixedSizeQueueStat implements Stat{
    List<Candlestick> candleRepo = new ArrayList<>();
    Candlestick poppedCandle;
    int objectiveDataSize, currentDataSize = 0;
    double statValue = 0;
    Operator operator;

    String ref;

    public FixedSizeQueueStat(int dataSize, OperatorType operatorType, String ref) throws Exception {
        operator = Operator.getOperator(operatorType);
        this.objectiveDataSize = dataSize;
        this.ref = ref;
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

    @Override
    public double getValue() {
        return statValue;
    }

    public void updateStatValue() {
        statValue = operator.calculate(this);
    }

    @Override
    public double getCandleValueByRef(Candlestick candlestick) throws Exception {
        switch (ref){
            case "close":
                return Double.parseDouble(candlestick.getClose());
            case "open":
                return Double.parseDouble(candlestick.getOpen());
            case "high":
                return Double.parseDouble(candlestick.getHigh());
            case "low":
                return Double.parseDouble(candlestick.getLow());
        }
        throw new Exception("Wrong Ref In Stat Class");
    }

    public Candlestick getPoppedCandle() {
        return poppedCandle;
    }

    public int getCurrentDataSize() {
        return currentDataSize;
    }

    public List<Candlestick> getCandleRepo() {
        return candleRepo;
    }

    public double getStatValue() {
        return statValue;
    }
}
