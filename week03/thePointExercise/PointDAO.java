package thePointExercise;

import jakarta.persistence.*;
import unicorn.UnicornDAO;

import java.util.List;

@NamedQuery(name= "PointDAO.findAmountOfPoints",query = "SELECT COUNT(p) FROM Point p")
public class PointDAO {
    private static EntityManagerFactory emf;
    private static PointDAO instance;

    public PointDAO() {
    }
    public static PointDAO getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new PointDAO();
        }
        return instance;
    }

    public void saveAllPoints() {
        // Store 1000 Point objects in the database:
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            for (int i = 0; i < 1000; i++) {
                Point p = new Point(i, i);
                em.persist(p);
            }
            em.getTransaction().commit();
        }
    }

    public int findAmountOfPoints() {
        try(EntityManager em = emf.createEntityManager()) {
            // Find the number of Point objects in the database:
            Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
            return Integer.parseInt(q1.getSingleResult().toString());
        }
    }

    public Double findPointAverage() {
        try(EntityManager em = emf.createEntityManager()) {
            // Find the average X value:
            Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
            return Double.parseDouble(q2.getSingleResult().toString());
        }
    }

    public List<Point> findAll() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
            List<Point> results = query.getResultList();
            return results;
        }
    }
}
