import com.binance.api.client.domain.market.Candlestick;
import data_filterer.DataFilter;
import data_filterer.NoiseFilter;
import data_scanner.BinanceDataScanner;
import data_scanner.DataScanner;
import string_serializer.CandleJsonSerializer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DataScanner scanner = new BinanceDataScanner();
        while (true) {
            HashMap<String, List<Object>> hashMap = scanner.configure().scanData();
            DataFilter noiseFilter = new NoiseFilter();
            for (String key : hashMap.keySet()){
                List<Object> dataList = hashMap.get(key);
                dataList =  new ArrayList<>(new CandleJsonSerializer().serializeMany(noiseFilter.filter(dataList)));
                hashMap.put(key, dataList);
            }
            System.out.println(hashMap.get("BTCUSDT").size());
            System.out.println(hashMap);
            scanner.waitForNextRequestMoment();
        }
    }
}
