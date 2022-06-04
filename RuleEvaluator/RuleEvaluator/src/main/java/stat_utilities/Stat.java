package stat_utilities;

import com.binance.api.client.domain.market.Candlestick;

public interface Stat {

    public void addCandleRecord(Candlestick candlestick);

    public void updateIndex();

    public boolean isReady();

    public void updateStatValue();
}
