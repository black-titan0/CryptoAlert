package data_scanner;

import com.binance.api.client.domain.market.Candlestick;

import java.util.HashMap;
import java.util.List;

public interface DataScanner {

    public DataScanner configure();
    public HashMap<String, List<Candlestick>> scanData() throws Exception;

    void waitForNextRequestMoment() throws InterruptedException;
}
