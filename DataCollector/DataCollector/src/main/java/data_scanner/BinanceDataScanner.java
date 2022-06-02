package data_scanner;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import properties_reader.PropertiesReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BinanceDataScanner implements DataScanner{

    BinanceApiRestClient restClient;
    List<String> markets;
    int waitTime = 5;


    @Override
    public DataScanner configure() {
        restClient = BinanceApiClientFactory.newInstance().newRestClient();
        PropertiesReader propertiesReader = new PropertiesReader();
        waitTime = Integer.parseInt(propertiesReader.readFromConfig("update_intervals", "data_collector_config"));
        setMarkets(propertiesReader.readFromConfig("markets", "data_collector_config")
                .replaceAll(" +", "")
                .split(","));
        return this;
    }




    private void setMarkets(String[] marketNames) {
         markets = Arrays.asList(marketNames);
    }


    @Override
    public HashMap<String, List<Candlestick>> scanData() throws Exception {
        if (restClient == null){
            throw new Exception("DataScanner: usage before configuration");
        }
        HashMap <String, List<Candlestick>> marketData = new HashMap<>();
        for (String marketName: markets) {
            List<Candlestick> candlesticks = restClient.getCandlestickBars(marketName, CandlestickInterval.ONE_MINUTE);
            marketData.put(marketName, candlesticks);
        }
        return marketData;
    }
}
