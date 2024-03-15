package excerciseWithJavalinAndCrud;

import excerciseWithJavalinAndCrud.Utils.TokenUtils;
import excerciseWithJavalinAndCrud.config.ApplicationConfig;
import excerciseWithJavalinAndCrud.config.HibernateConfig;
import excerciseWithJavalinAndCrud.config.Routes;
import excerciseWithJavalinAndCrud.controllers.HotelController;
import excerciseWithJavalinAndCrud.controllers.RoomController;
import excerciseWithJavalinAndCrud.controllers.UserController;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import excerciseWithJavalinAndCrud.secrurity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

public class Main {
    public static void main(String[] args) {

        HotelController hotelController = new HotelController(false);
        RoomController roomController = new RoomController(false);
        UserController userController = new UserController(false);
        TokenUtils utils = new TokenUtils();

   /*     EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        User user = new User("Niklas","21344");
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        try{
            User toVerify = new User("Niklas","21344");
            User userVerified = UserController.getVerifiedUser(toVerify);
            System.out.println(userVerified.getUsername());
        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }*/

        /*try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel hotel1 = new Hotel("Charmander","Ildvej 5");

            Room room1 = new Room(hotel1.getId(),1,5000);
            Room room2 = new Room(hotel1.getId(),2,3000);

            hotel1.addRoom(room1);
            hotel1.addRoom(room2);
            em.persist(hotel1);
            em.getTransaction().commit();
        }*/

        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7007)
                .setExceptionOverallHandling()
                .checkSecurityRoles()
                .setRoute(Routes.setRoutes());
    }
}
