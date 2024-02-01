package opgave8;

import java.util.ArrayList;

public class MemoryStorage<T> implements  DataStorage<T>{
    ArrayList<T> storage = new ArrayList<>();

    @Override
    public String store(T data) {
        if(data != null){
            storage.add(data);
            return "Data stored successfully";
        }
        return "Data storage was unsuccessfully";
    }
    @Override
    public T retrieve(String source) {
        if(storage.contains(source)){
            int sourceIndex = storage.indexOf(source);
            return storage.get(sourceIndex);
        }
        return null;
    }
}
