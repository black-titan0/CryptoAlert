package string_serializer;

import java.util.List;

public interface ObjectToStringSerializer {
    public String serialize(Object object);
    public List<String> serializeMany(List<Object> objects);
}
