package thePointExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointDAOTest {
    static EntityManagerFactory emf;
    static EntityManager em;
    static PointDAO pointDAO;
    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        em = emf.createEntityManager();
        pointDAO = PointDAO.getInstance(emf);
    }
    @AfterAll
    static void afterAll(){
        emf.close();
        em.close();
    }

    @Test
    void saveAllPoints() {
          //pointDAO.saveAllPoints();
    }

    @Test
    void findAmountOfPoints() {
        int sum = pointDAO.findAmountOfPoints();
        System.out.println(sum);
        assertEquals(1000,sum);
    }

    @Test
    void findPointAverage() {
        Double average = pointDAO.findPointAverage();
        assertEquals(499.5,average);
    }

    @Test
    void findAll() {
        List<Point> points = pointDAO.findAll();
        assertEquals(1000,points.size());
    }
}