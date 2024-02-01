package opgave8;

import java.util.ArrayList;

public class MemoryStorage<T> implements  DataStorage<T>{
    ArrayList<T> storage = new ArrayList<>();

    @Override
    public String store(T data) {
        if(data != null){
            storage.add(data);
            String id = ""+(storage.size()-1);
            return id;
        }
        return "Data storage was unsuccessfully";
    }
    @Override
    public T retrieve(String source) {
        if(source != null){
            if(source.matches("[0-9]+")) {
                int id = Integer.parseInt(source);
                if (id <= storage.size()) {
                    return storage.get(id);
                }
            }
        }
        return null;
    }
}
