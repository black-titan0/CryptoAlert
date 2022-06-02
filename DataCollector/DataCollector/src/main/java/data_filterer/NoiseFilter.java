package data_filterer;

import com.binance.api.client.domain.market.Candlestick;

import java.util.ArrayList;
import java.util.List;

public class NoiseFilter implements DataFilter{
    @Override
    public List<Object> filter(List<Object> dataList) {
        ArrayList<Object> newDataList = new ArrayList<>(dataList);
        newDataList.removeIf(datum -> !isValid(datum));
        return newDataList;
    }

    private boolean isValid(Object datum){
        if (!(datum instanceof Candlestick))
            return false;
        Candlestick candlestick = (Candlestick)datum;
        try {
            Double.parseDouble(candlestick.getHigh());
            Double.parseDouble(candlestick.getClose());
            Double.parseDouble(candlestick.getLow());
            Double.parseDouble(candlestick.getOpen());
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
