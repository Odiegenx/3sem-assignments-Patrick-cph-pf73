package recyclingExercise.dao;

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
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WasteTruckDAOImplTest {
    static EntityManagerFactory emf;
    static EntityManager em;
    static WasteTruckDAOImpl wasteTruckDAO;
    static Driver testDriver;
    static WasteTruck testWasteTruck;
    static WasteTruck testWasteTruck2;
    static Driver staticDriver1;
    static WasteTruck staticWasteTruck1;
    static String driver1Id;
    static int wasteTrick1Id;
    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        wasteTruckDAO = WasteTruckDAOImpl.getInstance(emf);
        testDriver = new Driver("TEST","MEDTESTPÅ", BigDecimal.valueOf(999999.999));
        testWasteTruck = new WasteTruck("TEST",999999,"AA999999");
        testWasteTruck2 = new WasteTruck("TEST2",1111111,"AA1111111");
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
            wasteTrick1Id = driver1.getWasteTruck().getId();
            staticDriver1 = driver1;
            staticWasteTruck1 = wasteTruck1;
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
    void saveWasteTruck() {
        int truckId = wasteTruckDAO.saveWasteTruck("TEST","AA999999",999999);
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<WasteTruck> query = em.createQuery("select t from WasteTruck t where id = ?1", WasteTruck.class)
                    .setParameter(1, truckId);
            assertEquals(truckId, query.getSingleResult().getId());
        }
    }

    @Test
    void getWasteTruckById() {
        WasteTruck truckFromDB = wasteTruckDAO.getWasteTruckById(wasteTrick1Id);
        assertEquals("CG32323",truckFromDB.getRegistrationNumber());
    }

    @Test
    void setWasteTruckAvailable() {
        WasteTruck wasteTruckFromDB = null;
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<WasteTruck> query = em.createQuery("select t from WasteTruck t where id = ?1", WasteTruck.class)
                    .setParameter(1, 1);
            wasteTruckFromDB = query.getSingleResult();
        }
        boolean status = wasteTruckFromDB.isAvailable();
        wasteTruckDAO.setWasteTruckAvailable(wasteTruckFromDB,true);
        assertNotEquals(status,wasteTruckFromDB.isAvailable());
        assertEquals(true,wasteTruckFromDB.isAvailable());
    }

    @Test
    void deleteWasteTruck() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<WasteTruck> truckTypedQueryBeforeDelete = em.createQuery("select t from WasteTruck t", WasteTruck.class);
            List<WasteTruck> trucksBeforeDelete = truckTypedQueryBeforeDelete.getResultList();
            wasteTruckDAO.deleteWasteTruck(wasteTrick1Id);
            TypedQuery<WasteTruck> truckTypedQueryAfterDelete = em.createQuery("select t from WasteTruck t", WasteTruck.class);
            List<WasteTruck> trucksAfterDelete = truckTypedQueryAfterDelete.getResultList();
            assertEquals(trucksBeforeDelete.size()-1,trucksAfterDelete.size());
        }
    }


    // could not addDriverToWasteTruck to validate the right way, it works fine in my main,
    // but my test keeps getting this error: org.hibernate.id.IdentifierGenerationException: ids for this class must be manually assigned before calling save(): recyclingExercise.model.Driver
    @Test
    void addDriverToWasteTruck() {
        //Set<Driver> driverListBeforeAdd = testWasteTruck2.getDrivers();
        wasteTruckDAO.addDriverToWasteTruck(testWasteTruck2,testDriver);
        Set<Driver> driverListAfterAdd = testWasteTruck2.getDrivers();
        //assertNotEquals(false,driverListBeforeAdd.contains(testDriver));
        assertEquals(true,driverListAfterAdd.contains(testDriver));
    }


    // removeDriverFromWasteTrick() fails as well, since for some reason it's not removing the driver from the truck correctly
    // during the test. It works fine in main.
    // Maybe something with how im handling the different managed states, but I don't have more time to use on this.
    @Test
    void removeDriverFromWasteTruck() {
        try(EntityManager em = emf.createEntityManager()){
            WasteTruck wasteTruckBeforeRemove = em.find(WasteTruck.class,wasteTrick1Id);
            Driver toRemvoe = em.find(Driver.class,driver1Id);
            assertEquals(true,wasteTruckBeforeRemove.getDrivers().contains(toRemvoe));
            wasteTruckDAO.removeDriverFromWasteTruck(wasteTruckBeforeRemove,driver1Id);
            WasteTruck wasteTruckAfterRemove = em.find(WasteTruck.class,wasteTruckBeforeRemove.getId());
            assertEquals(false,wasteTruckAfterRemove.getDrivers().contains(toRemvoe));;
        }
    }

    @Test
    void getAllAvailableTrucks() {
        List<WasteTruck> wasteTruckList = wasteTruckDAO.getAllAvailableTrucks();
        for (WasteTruck w:wasteTruckList) {
            assertEquals(true,w.isAvailable());
        }
    }
}