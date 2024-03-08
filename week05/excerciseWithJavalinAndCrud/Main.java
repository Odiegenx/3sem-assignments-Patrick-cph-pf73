package excerciseWithJavalinAndCrud;

import excerciseWithJavalinAndCrud.config.ApplicationConfig;
import excerciseWithJavalinAndCrud.config.HibernateConfig;
import excerciseWithJavalinAndCrud.config.Routes;
import excerciseWithJavalinAndCrud.controllers.HotelController;
import excerciseWithJavalinAndCrud.controllers.RoomController;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        HotelController hotelController = new HotelController();
        RoomController roomController = new RoomController();

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

       /* try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel hotel1 = new Hotel("Charmander","Ildvej 5");
            em.persist(hotel1);
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
                .setRoute(Routes.setRoutes());
    }
}
