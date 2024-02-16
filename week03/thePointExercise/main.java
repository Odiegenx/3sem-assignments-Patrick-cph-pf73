package thePointExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        EntityManager em = emf.createEntityManager();
        PointDAO pointDAO = PointDAO.getInstance(emf);
        pointDAO.saveAllPoints();
        int amountOfPoints = pointDAO.findAmountOfPoints();
        System.out.println("Amount of points: " + amountOfPoints);
        double average = pointDAO.findPointAverage();
        System.out.println("Average X: " + average);
        List<Point> points = pointDAO.findAll();
        points.forEach(System.out::println);
    }
}
