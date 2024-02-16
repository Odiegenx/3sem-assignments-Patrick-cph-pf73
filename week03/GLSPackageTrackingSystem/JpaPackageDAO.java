package GLSPackageTrackingSystem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JpaPackageDAO implements PackageDAO {
    private static EntityManagerFactory emf;
    private static JpaPackageDAO instance;

    public static JpaPackageDAO getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new JpaPackageDAO();
        }
        return instance;
    }
    @Override
    public void create(Package p) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if(p != null) {
                em.persist(p);
                em.getTransaction().commit();
            }
        }
    }
    @Override
    public Package read(String trackingNumber) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Package> query = em.createQuery("select p from Package p where trackingNumber = ?1", Package.class)
                    .setParameter(1,trackingNumber);
            return query.getSingleResult();
        }
    }
    @Override
    public Package update(Package o, String trackingNumber) {
        try(EntityManager em = emf.createEntityManager()){
                em.getTransaction().begin();
                TypedQuery<Package> query = em.createQuery("select p from Package p where trackingNumber = ?1", Package.class)
                        .setParameter(1,trackingNumber);
                Package tmpP = query.getSingleResult();
                if(tmpP != null){
                    o.setId(tmpP.getId());
                    Package toMerge = em.merge(o);
                    em.getTransaction().commit();
                    return toMerge;
                }
                em.getTransaction().rollback();
                return null;
            }
    }
    public Package updateStatus(Package.DeliveryStatus deliveryStatus, String trackingNumber){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Package> query = em.createQuery("select p from Package p where trackingNumber = ?1", Package.class)
                    .setParameter(1,trackingNumber);
            Package tmpP = query.getSingleResult();
            if(tmpP != null){
                tmpP.setDeliveryStatus(deliveryStatus);
                Package toMerge = em.merge(tmpP);
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
            TypedQuery<Package> query = em.createQuery("select p from Package p where trackingNumber = ?1", Package.class)
                    .setParameter(1,trackingNumber);
            Package tmpPackage = query.getSingleResult();
            em.remove(tmpPackage);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Package> readAll() {
        try(EntityManager em = emf.createEntityManager()){
            var query = em.createQuery("SELECT p from Package p", Package.class);
            return query.getResultList();
        }
    }
}
