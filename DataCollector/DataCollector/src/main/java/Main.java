import data_scanner.BinanceDataScanner;
import data_scanner.DataScanner;

import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DataScanner scanner = new BinanceDataScanner();
        while (true) {
            HashMap hashMap = scanner.configure().scanData();
            System.out.println(((List) hashMap.get("BTCUSDT")).size());
            System.out.println(hashMap);
            scanner.waitForNextRequestMoment();
        }
    }
}
