package recyclingExercise.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import recyclingExercise.model.Driver;

import java.math.BigDecimal;
import java.util.List;

public class DriverDAOImpl implements IDriverDAO {
    private static EntityManagerFactory emf;
    private static DriverDAOImpl instance;

    public static DriverDAOImpl getInstance(EntityManagerFactory emf_){
        if(instance == null){
            emf = emf_;
            instance = new DriverDAOImpl();
        }
        return instance;
    }

    @Override
    public String saveDriver(String name, String surname, BigDecimal salary){
        String id = "";
        Driver tmpD = null;
        if(name != null || surname != null){
            tmpD = new Driver(name, surname, salary);
        }
        if(tmpD != null) {
            try (EntityManager em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.persist(tmpD);
                id = tmpD.getId();
                em.getTransaction().commit();
            }
            return id;
        }
        return null;
    }

    @Override
    public Driver getDriverById(String id) {
        Driver driver = null;
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where id = ?1", Driver.class)
                    .setParameter(1,id);
            driver = driverTypedQuery.getSingleResult();
        }
        return driver;
    }

    @Override
    public Driver updateDriver(Driver driver) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where id = ?1", Driver.class)
                    .setParameter(1,driver.getId());
            Driver tmpDriver = driverTypedQuery.getSingleResult();
            if(tmpDriver != null){
                driver.setId(tmpDriver.getId());
                Driver toMerge = em.merge(driver);
                em.getTransaction().commit();
                return toMerge;
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void deleteDriver(String id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where id = ?1", Driver.class)
                    .setParameter(1,id);
            Driver tmpDriver = driverTypedQuery.getSingleResult();
            em.remove(tmpDriver);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Driver> findAllDriversEmployedAtTheSameYear(String year) {
        List<Driver> driverList = null;
        try(EntityManager em = emf.createEntityManager()){
         TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where year(d.employmentDate) = ?1", Driver.class)
                 .setParameter(1,year);
         driverList = driverTypedQuery.getResultList();
        }
        return driverList;
    }

    @Override
    public List<Driver> fetchAllDriversWithSalaryGreaterThan10000() {
        List<Driver> driverList = null;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Driver> driverTypedQuery = em.createQuery("select d from Driver d where d.salary>10000", Driver.class);
            driverList = driverTypedQuery.getResultList();
        }
        return driverList;
    }

    @Override
    public double fetchHighestSalary() {
        double higestSalary = 0.0;
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<BigDecimal> query = em.createQuery("SELECT max(d.salary) from Driver d",BigDecimal.class);
            higestSalary = query.getSingleResult().doubleValue();
        }
        return higestSalary;
    }

    @Override
    public List<String> fetchFirstNameOfAllDrivers() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<String> namesQuery = em.createQuery("select d.name from Driver  d",String.class);
            return namesQuery.getResultList();
        }
    }

    @Override
    public long calculateNumberOfDrivers() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Long> query = em.createQuery("select count(d) from Driver d ",Long.class);
            return query.getSingleResult();
        }
    }

    @Override
    public Driver fetchDriverWithHighestSalary() {
        Driver driver = null;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Driver> query = em.createQuery("select d from Driver d  group by d order by max(d.salary) desc", Driver.class);
            query.setMaxResults(1);
            driver = query.getSingleResult();
        }
        return driver;
    }
}
