package opgave8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryStorage<T> implements  DataStorage<T>{
    Map<String, T> mapStorage = new HashMap<>();

    @Override
    public String store(T data) {
        if(data != null) {
            int key = mapStorage.size();
            String strKey = "" + key;
            mapStorage.put(strKey, data);
            return strKey;
        }
        return "Data storage was unsuccessfully";
    }
    @Override
    public T retrieve(String source) {
        if(source != null) {
            return mapStorage.get(source);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        mapStorage.remove(key);
    }

    @Override
    public T retrieveTest(String doubleKey) {
        return null;
    }
}
