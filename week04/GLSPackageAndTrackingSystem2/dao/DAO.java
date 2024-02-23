package GLSPackageAndTrackingSystem2.dao;

import java.util.List;

public interface DAO<T> {
    public void create(T t);
    public T read(String trackingNumber);
    public T update(T t,String trackingNumber);
    public void delete(String trackingNumber);
     List<T> readAll();
}
