package data_filterer;

import java.util.List;

public interface DataFilter {
    public List<Object> filter(List<Object> dataList);
}
