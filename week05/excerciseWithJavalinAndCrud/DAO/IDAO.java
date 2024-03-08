package excerciseWithJavalinAndCrud.DAO;

import java.util.List;

public interface IDAO<T,D> {
    public T getById(D id);
    public void create(T t);
    public T update(T t,D id);
    public void delete(D id);
    public List<T> getAll();
}
