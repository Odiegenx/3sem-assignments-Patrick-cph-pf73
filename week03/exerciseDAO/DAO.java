package exerciseDAO;

import java.util.List;

public interface DAO <T> {
    public void create(T t);
    public T read(int id);
    public T update(T t,int id);
    public void delete(int id);
     List<T> readAll();
}
