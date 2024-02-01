package opgave8;

public class FileStorage<T> implements DataStorage<T>{

    FileStorage<T> storage = new FileStorage<>();
    @Override
    public String store(T data) {
        if(data != null) {
            storage.store(data);
        }
        return null;
    }

    @Override
    public T retrieve(String source) {
        return storage.retrieve(source);
    }
}
