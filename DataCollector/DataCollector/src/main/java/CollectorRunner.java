import data_filterer.DataFilter;
import data_filterer.NoiseFilter;
import data_scanner.BinanceDataScanner;
import data_scanner.DataScanner;
import kafka_utilities.CandleProducer;
import properties_reader.PropertiesReader;
import string_serializer.CandleJsonSerializer;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

public class CollectorRunner {
    boolean isRunning = true;
    public void run() throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader();
        String host = propertiesReader.readFromConfig("kafka_host", "data_collector_config");
        int port = Integer.parseInt(propertiesReader.readFromConfig("kafka_port", "data_collector_config"));
        CandleProducer producer = new CandleProducer(InetAddress.getLocalHost().getHostName(), host, port);
        DataScanner scanner = new BinanceDataScanner();
        while (isRunning) {
            HashMap<String, List<Object>> hashMap = scanner.configure().scanData();
            DataFilter noiseFilter = new NoiseFilter();
            for (String key : hashMap.keySet()){
                List<Object> dataList = hashMap.get(key);
                producer.publishMultipleCandles(key,
                        new CandleJsonSerializer().serializeMany(
                                noiseFilter.filter(dataList)
                        )
                );
            }
            scanner.waitForNextRequestMoment();
        }
    }

    public void stop(){
        isRunning = false;
    }
}
