package recyclingExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import recyclingExercise.config.HibernateConfig;
import recyclingExercise.dao.DriverDAOImpl;
import recyclingExercise.dao.WasteTruckDAOImpl;
import recyclingExercise.model.Driver;
import recyclingExercise.model.WasteTruck;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        DriverDAOImpl driverDAO = DriverDAOImpl.getInstance(emf);
        WasteTruckDAOImpl wasteTruckDAO = WasteTruckDAOImpl.getInstance(emf);

        Driver patrick = new Driver("Patrick","Hansen", BigDecimal.valueOf(40000.000));
        WasteTruck wasteTruck1 = new WasteTruck("bmw",2,"CG345623");
        patrick.addWasteTruck(wasteTruck1);

        // adds a driver to the db and returns the id, which is used further down to make sure that the same driver
        // that is updated has been deleted.
        String driverId = driverDAO.saveDriver("Patrick","Hansen",BigDecimal.valueOf(40000.000));
        Driver driverFromDB = driverDAO.getDriverById(driverId);
        System.out.println(driverFromDB);
        driverFromDB.setSurname("Bensen");
        Driver updatedDriverFromDB = driverDAO.updateDriver(driverFromDB);
        System.out.println(updatedDriverFromDB);
        ////////////////////////////////////
        System.out.println(driverDAO.findAllDriversEmployedAtTheSameYear("2023"));
        System.out.println(driverDAO.fetchAllDriversWithSalaryGreaterThan10000());
        System.out.println(driverDAO.fetchHighestSalary());
        System.out.println(driverDAO.calculateNumberOfDrivers());
        System.out.println(driverDAO.fetchDriverWithHighestSalary());
        ////////////////////////////////////
        int truckId = wasteTruckDAO.saveWasteTruck("bmw","BG23551",2);
        WasteTruck wasteTruckFromDB = wasteTruckDAO.getWasteTruckById(truckId);
        System.out.println(wasteTruckFromDB);
        wasteTruckDAO.setWasteTruckAvailable(wasteTruckFromDB,true);
        WasteTruck updatedWasteTruckFromDB = wasteTruckDAO.getWasteTruckById(truckId);
        System.out.println(updatedWasteTruckFromDB);

        // adds a driver to a waste truck and prints the driver that was added.
        wasteTruckDAO.addDriverToWasteTruck(updatedWasteTruckFromDB,updatedDriverFromDB);
        wasteTruckDAO.getWasteTruckById(truckId).getDrivers().forEach(System.out::println);
        System.out.println("------------");
        // removes a driver from a waste truck
        wasteTruckDAO.removeDriverFromWasteTruck(updatedWasteTruckFromDB,driverId);
        System.out.println(driverDAO.getDriverById(driverId));
        System.out.println("-----------");
        wasteTruckDAO.getAllAvailableTrucks().forEach(System.out::println);

        driverDAO.deleteDriver(driverId);
        wasteTruckDAO.deleteWasteTruck(truckId);
        ////////////////////////////////////

        /*    Date date = new Date();
        System.out.println(date);
        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

        // Format the Date object using the SimpleDateFormat object
        String formattedDate = dateFormat.format(date);
        System.out.println(formattedDate);
        String ptrick = "Patrick";
        System.out.println(ptrick.charAt(0));*/
  /*      Driver patrick = new Driver("Patrick","Hansen", BigDecimal.valueOf(40000.000));
        WasteTruck wasteTruck1 = new WasteTruck("bmw",2,"CG345623");
        patrick.addWasteTruck(wasteTruck1);
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(patrick);
            System.out.println(patrick.getId());
            if(validateDriverId(patrick.getId())) {
                em.getTransaction().commit();
            }else {
                em.getTransaction().rollback();
            }
        }*/
    }
}
