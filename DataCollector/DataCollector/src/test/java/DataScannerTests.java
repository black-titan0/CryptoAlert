import data_scanner.BinanceDataScanner;
import data_scanner.DataScanner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DataScannerTests {

private final DataScanner dataScanner = new BinanceDataScanner();

@Test
public void configurationTest() {
    dataScanner.configure();
    assertThat(dataScanner.getClass()).isEqualTo(BinanceDataScanner.class);
    BinanceDataScanner binanceDataScanner = ((BinanceDataScanner) dataScanner);
    assertThat(binanceDataScanner.getRestClient()).isNotNull();
}
@Test
public void scanDataTest() {
        dataScanner.configure();
        BinanceDataScanner binanceDataScanner = ((BinanceDataScanner) dataScanner);
        assertThat(binanceDataScanner.scanData());
    }
}
