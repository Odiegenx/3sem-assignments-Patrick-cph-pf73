package recyclingExercise.dao;

import dolphin.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recyclingExercise.config.HibernateConfig;
import recyclingExercise.model.Driver;
import recyclingExercise.model.WasteTruck;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverDAOImplTest {
    static EntityManagerFactory emf;
    static EntityManager em;
    static DriverDAOImpl driverDAO;
    static Driver testDriver;
    static WasteTruck testWasteTruck;
    static String driver1Id;
    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        driverDAO = DriverDAOImpl.getInstance(emf);
        testDriver = new Driver("TEST","MEDTESTPÅ", BigDecimal.valueOf(999999.999));
        testWasteTruck = new WasteTruck("TEST",999999,"AA999999");
        testDriver.addWasteTruck(testWasteTruck);
    }
    @BeforeEach
    void setUp() {
        try(EntityManager em = emf.createEntityManager()) {
            Driver driver1 = new Driver("Patrick","Hansen", BigDecimal.valueOf(40000.000));
            WasteTruck wasteTruck1 = new WasteTruck("bmw",2000,"CG32323");
            driver1.addWasteTruck(wasteTruck1);

            Driver driver2 = new Driver("Niklas","Jensen", BigDecimal.valueOf(2000.000));
            WasteTruck wasteTruck2 = new WasteTruck("bmw",2522,"HT2457623");
            driver2.addWasteTruck(wasteTruck2);

            Driver driver3 = new Driver("Rolin","Jørgensen", BigDecimal.valueOf(67400.000));
            WasteTruck wasteTruck3 = new WasteTruck("bmw",21558,"UH3623423");
            driver3.addWasteTruck(wasteTruck3);

            Driver driver4 = new Driver("Christian","Christiansen", BigDecimal.valueOf(200.000));
            WasteTruck wasteTruck4 = new WasteTruck("bmw",256669,"JUG3435323");
            driver4.addWasteTruck(wasteTruck4);

            em.getTransaction().begin();
            em.createQuery("delete from Driver ").executeUpdate();
            em.createQuery("delete from WasteTruck ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE waste_truck_id_seq RESTART WITH 1").executeUpdate();
            em.persist(driver1);
            driver1Id = driver1.getId();
            em.persist(driver2);
            em.persist(driver3);
            em.persist(driver4);
            em.getTransaction().commit();
        }
    }
    static void afterAll(){
        emf.close();
    }

    @Test
    void saveDriver() {
        String driverId = driverDAO.saveDriver("TEST","MEDTESTPÅ", BigDecimal.valueOf(999999.999));
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Driver> query = em.createQuery("select d from Driver d where id = ?1", Driver.class)
                    .setParameter(1, driverId);
            assertEquals(driverId, query.getSingleResult().getId());
        }
    }

    @Test
    void getDriverById(){
        Driver driverFromDB = driverDAO.getDriverById(driver1Id);
        assertEquals("Patrick",driverFromDB.getName());
    }

    @Test
    void updateDriver() {
        try(EntityManager em = emf.createEntityManager()) {
            Driver tmpDriver = em.find(Driver.class, driver1Id);
            tmpDriver.setSurname(testDriver.getSurname());
            Driver updatedDriver = driverDAO.updateDriver(tmpDriver);
            Driver tmpUpdatedDriverFromDB = em.find(Driver.class,updatedDriver.getId());
            assertEquals(testDriver.getSurname(), tmpUpdatedDriverFromDB.getSurname());
        }
    }

    @Test
    void deleteDriver() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Driver> driverTypedQueryBeforeDelete = em.createQuery("select d from Driver d", Driver.class);
            List<Driver> driverListBeforeDelete = driverTypedQueryBeforeDelete.getResultList();
            driverDAO.deleteDriver(driver1Id);
            TypedQuery<Driver> driverTypedQueryAfterDelete = em.createQuery("select d from Driver d", Driver.class);
            List<Driver> driverListAfterDelete = driverTypedQueryAfterDelete.getResultList();
            assertEquals(driverListBeforeDelete.size()-1,driverListAfterDelete.size());
        }
    }

    @Test
    void findAllDriversEmployedAtTheSameYear() {
        List<Driver> tmpDrivers = driverDAO.findAllDriversEmployedAtTheSameYear("2024");
        assertEquals(4,tmpDrivers.size());
    }

    @Test
    void fetchAllDriversWithSalaryGreaterThan10000() {
        List<Driver> tmpDrivers = driverDAO.fetchAllDriversWithSalaryGreaterThan10000();
        assertEquals(2,tmpDrivers.size());
    }

    @Test
    void fetchHighestSalary() {
        double higestSalary = driverDAO.fetchHighestSalary();
        assertEquals(67400.00,higestSalary);
    }

    @Test
    void fetchFirstNameOfAllDrivers() {
        List<String> names = driverDAO.fetchFirstNameOfAllDrivers();
        List<String> actualNames = Arrays.asList("Patrick","Niklas","Rolin","Christian");
        for (int i = 0;i < actualNames.size();i++) {
            assertEquals(actualNames.get(i),names.get(i));
        }
    }

    @Test
    void calculateNumberOfDrivers() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d", Driver.class);
            List<Driver> driverList = driverTypedQuery.getResultList();
            assertEquals(driverList.size(), driverDAO.calculateNumberOfDrivers());
        }
    }

    @Test
    void fetchDriverWithHighestSalary() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where d.salary = 67400.000", Driver.class);
            Driver driver1 = driverTypedQuery.getSingleResult();
            Driver driverWithHighestSalary = driverDAO.fetchDriverWithHighestSalary();
            assertEquals(driver1.getSalary(), driverWithHighestSalary.getSalary());
        }
    }
}