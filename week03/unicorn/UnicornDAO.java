package unicorn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UnicornDAO {
    private static EntityManagerFactory emf;
    private static UnicornDAO instance;

    public UnicornDAO() {
    }

    public static UnicornDAO getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new UnicornDAO();
        }
        return instance;
    }

    public int save(Unicorn unicorn){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(unicorn);
            em.getTransaction().commit();
        }
        return unicorn.getId();
    }
    public Unicorn update(Unicorn unicorn, Integer integer){
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            var h = em.find(Unicorn.class,integer);
            if(h != null) {
                unicorn.setId(h.getId());
                Unicorn merge = em.merge(unicorn);
                em.getTransaction().commit();
                return merge;
            }
            return null;
        }
    }
    public void delete(int id){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Unicorn unicorn = em.find(Unicorn.class,id);
            em.remove(unicorn);
            em.getTransaction().commit();
        }
    }
    public Unicorn findById(Integer id){
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Unicorn.class,id);
        }
    }
    public List<Unicorn> findAll(){
        try(EntityManager em = emf.createEntityManager()){
            var query = em.createQuery("SELECT u from Unicorn u", Unicorn.class);
            return query.getResultList();
        }
    }
}
