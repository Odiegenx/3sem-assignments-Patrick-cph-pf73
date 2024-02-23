package recyclingExercise.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import recyclingExercise.model.Driver;
import recyclingExercise.model.WasteTruck;

import java.util.ArrayList;
import java.util.List;

public class WasteTruckDAOImpl implements IWasteTruckDAO{
    private static EntityManagerFactory emf;
    private static WasteTruckDAOImpl instance;

    public static WasteTruckDAOImpl getInstance(EntityManagerFactory emf_){
        if(instance == null){
            emf = emf_;
            instance = new WasteTruckDAOImpl();
        }
        return instance;
    }
    @Override
    public int saveWasteTruck(String brand, String registrationNumber, int capacity) {
        int tmpId = 0;
        WasteTruck tmpTruck = null;
        if(brand != null || registrationNumber != null) {
             tmpTruck = new WasteTruck(brand, capacity, registrationNumber);
        }
        if(tmpTruck != null){
            try(EntityManager em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.persist(tmpTruck);
                tmpId = tmpTruck.getId();
                em.getTransaction().commit();
            }
        }
        return tmpId;
    }

    @Override
    public WasteTruck getWasteTruckById(int id) {
        WasteTruck truck = null;
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<WasteTruck> wasteTruckTypedQuery = em.createQuery("select t from WasteTruck t where id = ?1", WasteTruck.class)
                    .setParameter(1,id);
            truck = wasteTruckTypedQuery.getSingleResult();
        }
        return truck;
    }

    @Override
    public void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available) {
        try(EntityManager em = emf.createEntityManager()){
            WasteTruck tmpTruck = em.find(WasteTruck.class,wasteTruck.getId());
            if(tmpTruck != null){
                wasteTruck.setAvailable(available);
                em.getTransaction().begin();
                em.merge(wasteTruck);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void deleteWasteTruck(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            WasteTruck tmpTruck = em.find(WasteTruck.class, id);
            if(tmpTruck != null) {
                if(tmpTruck.getDrivers() != null) {
                    for (int i = 0; i < tmpTruck.getDrivers().size(); i++) {
                        tmpTruck.getDrivers().forEach(x -> x.setWasteTruck(null));
                    }
                }
                em.getTransaction().begin();
                em.remove(tmpTruck);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver) {
        if(wasteTruck != null || driver != null) {
            driver.addWasteTruck(wasteTruck);
            wasteTruck.addDriver(driver);
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                if (wasteTruck.getId() == null) {
                    em.persist(wasteTruck);
                }
                if (driver.getId() == null) {
                    em.persist(driver);
                }
                em.merge(wasteTruck);
                em.merge(driver);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Driver driver = em.find(Driver.class,id);
            WasteTruck managedTruck = em.find(WasteTruck.class, wasteTruck.getId());
            if(driver != null && managedTruck.getDrivers().contains(driver)) {
                    driver.setWasteTruck(null);
                    managedTruck.getDrivers().remove(driver);
                    em.merge(driver);
                    em.merge(managedTruck);
                    em.getTransaction().commit();
            }
        }
    }

    @Override
    public List<WasteTruck> getAllAvailableTrucks() {
        List<WasteTruck> wasteTruckList = null;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<WasteTruck> wasteTruckTypedQuery = em.createQuery("select t from WasteTruck t where isAvailable = true ", WasteTruck.class);
            wasteTruckList = wasteTruckTypedQuery.getResultList();
        }
        return wasteTruckList;
    }
}
