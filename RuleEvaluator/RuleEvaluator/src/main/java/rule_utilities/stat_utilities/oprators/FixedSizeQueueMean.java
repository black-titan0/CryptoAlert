package rule_utilities.stat_utilities.oprators;

import com.binance.api.client.domain.market.Candlestick;
import rule_utilities.stat_utilities.FixedSizeQueueStat;
import rule_utilities.stat_utilities.Stat;

public class FixedSizeQueueMean implements Operator {
    @Override
    public double calculate(Stat stat) {
        FixedSizeQueueStat fixedSizeQueueStat = ((FixedSizeQueueStat) stat);
        Candlestick popped = fixedSizeQueueStat.getPoppedCandle();
        var candles = fixedSizeQueueStat.getCandleRepo();
        var size = fixedSizeQueueStat.getCurrentDataSize();
        try {
            if (popped == null)
                return (fixedSizeQueueStat.getStatValue() + fixedSizeQueueStat.getCandleValueByRef(candles.get(size - 1))) / size;
            return (fixedSizeQueueStat.getStatValue()
                    + fixedSizeQueueStat.getCandleValueByRef(candles.get(size - 1))
                    - fixedSizeQueueStat.getCandleValueByRef(popped)
            ) / size;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fixedSizeQueueStat.getStatValue();
    }
}
