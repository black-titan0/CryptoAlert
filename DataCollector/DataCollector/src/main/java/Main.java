import data_scanner.BinanceDataScanner;
import data_scanner.DataScanner;

public class Main {

    public static void main(String[] args) throws Exception {
        DataScanner scanner = new BinanceDataScanner();
        System.out.println(scanner.configure().scanData());
    }
}
