package data_scanner;

import java.util.HashMap;
import java.util.List;

public interface DataScanner {

    public DataScanner configure();
    public HashMap<String, List<Object>> scanData() throws Exception;

    void waitForNextRequestMoment() throws InterruptedException;
}
