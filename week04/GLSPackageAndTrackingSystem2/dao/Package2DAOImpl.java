package GLSPackageAndTrackingSystem2.dao;

import GLSPackageAndTrackingSystem2.Location;
import GLSPackageAndTrackingSystem2.Package2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Package2DAOImpl implements Package2DAO{

    private static EntityManagerFactory emf;
    private static Package2DAOImpl instance;

    public static Package2DAOImpl getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new Package2DAOImpl();
        }
        return instance;
    }
    @Override
    public void create(Package2 package2) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if(package2 != null) {
                em.persist(package2);
                em.getTransaction().commit();
            }
        }
    }
    @Override
    public Package2 read(String trackingNumber) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Package2> query = em.createQuery("select p from Package2 p where trackingNumber = ?1", Package2.class)
                    .setParameter(1,trackingNumber);
            return query.getSingleResult();
        }
    }
    @Override
    public Package2 update(Package2 o, String trackingNumber) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Package2> query = em.createQuery("select p from Package2 p where trackingNumber = ?1", Package2.class)
                    .setParameter(1,trackingNumber);
            Package2 tmpP = query.getSingleResult();
            if(tmpP != null){
                o.setId(tmpP.getId());
                Package2 toMerge = em.merge(o);
                em.getTransaction().commit();
                return toMerge;
            }
            em.getTransaction().rollback();
            return null;
        }
    }
    public Package2 updateStatus(Package2.DeliveryStatus deliveryStatus, String trackingNumber){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Package2> query = em.createQuery("select p from Package2 p where trackingNumber = ?1", Package2.class)
                    .setParameter(1,trackingNumber);
            Package2 tmpP = query.getSingleResult();
            if(tmpP != null){
                tmpP.setDeliveryStatus(deliveryStatus);
                Package2 toMerge = em.merge(tmpP);
                em.getTransaction().commit();
                return toMerge;
            }
            em.getTransaction().rollback();
            return null;
        }
    }
    @Override
    public void delete(String trackingNumber) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Package2> query = em.createQuery("select p from Package2 p where trackingNumber = ?1", Package2.class)
                    .setParameter(1,trackingNumber);
            Package2 tmpPackage = query.getSingleResult();
            em.remove(tmpPackage);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Package2> readAll() {
        try(EntityManager em = emf.createEntityManager()){
            var query = em.createQuery("SELECT p from Package2 p", Package2.class);
            return query.getResultList();
        }
    }
}
