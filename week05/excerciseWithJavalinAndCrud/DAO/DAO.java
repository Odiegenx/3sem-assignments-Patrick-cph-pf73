package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class DAO<T, D> implements IDAO<T,D> {

    Class<T> objectClass;
    static boolean isTest;
    public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(isTest);

    public DAO(Class<T> tClass,boolean isTest){
        this.objectClass = tClass;
        this.isTest = isTest;
    }
    public T getById(D id) {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(objectClass,id);
        }
    }
    public T create(T t) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return t;
        }
    }
    public T update(T t, D id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T toMerge = em.find(objectClass,id);
            if(toMerge != null){
                T toReturn = em.merge(t);
                em.getTransaction().commit();
                return toReturn;
            }
            return null;
        }
    }
    public void delete(D id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T toRemove = em.find(objectClass,id);
            em.remove(toRemove);
            em.getTransaction().commit();
        }
    }
    public List<T> getAll() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<T> query = em.createQuery("SELECT h from "+objectClass.getSimpleName()+" h",objectClass);
            List<T> queryList = query.getResultList();
            return queryList;
        }
    }

}
