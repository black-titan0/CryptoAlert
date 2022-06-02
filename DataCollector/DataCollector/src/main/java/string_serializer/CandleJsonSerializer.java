package string_serializer;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CandleJsonSerializer implements ObjectToStringSerializer{

    @Override
    public String serialize(Object object) {
       return new Gson().toJson(object);
    }
    public List<String> serializeMany(List<Object> objects){
       List<String> stringList = new ArrayList<String>();
        for (Object object: objects) {
            stringList.add(serialize(object));
        }
        return stringList;
    }
}
