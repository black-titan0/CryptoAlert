package data_filterer;

import java.util.List;

public interface DataFilter {
    List<Object> filter(List<Object> dataList);
}
