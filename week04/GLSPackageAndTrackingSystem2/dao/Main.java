package GLSPackageAndTrackingSystem2.dao;

import GLSPackageAndTrackingSystem2.Location;
import GLSPackageAndTrackingSystem2.Package2;
import GLSPackageAndTrackingSystem2.Shipment;
import GLSPackageAndTrackingSystem2.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        Package2DAOImpl package2DAO = Package2DAOImpl.getInstance(emf);

        package2DAO.readAll().forEach(System.out::println);

        /*try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            LocalDate createdAt = LocalDate.now();
            LocalDate updatedAt = LocalDate.now();

            Package2 package1 = new Package2("244rgaa", "Patrick", "Rolin", Package2.DeliveryStatus.PENDING, createdAt, updatedAt);
            em.persist(package1);
            Package2 package2 = new Package2("15523115aa", "Rolin", "Patrick", Package2.DeliveryStatus.PENDING, createdAt, updatedAt);
            em.persist(package2);
            Package2 package3 = new Package2("51541assa", "Julemand", "Barn", Package2.DeliveryStatus.PENDING, createdAt, updatedAt);
            em.persist(package3);

            Shipment shipment1 = new Shipment(createdAt);
            Shipment shipment2 = new Shipment(createdAt);
            Shipment shipment3 = new Shipment(createdAt);
            Shipment shipment4 = new Shipment(createdAt);


            Location source1 = new Location(0.0, 0.0, "Nordpolen");
            em.persist(source1);

            Location destination1 = new Location(9.9, 9.9, "Sydpolen");
            em.persist(destination1);
            Location destination2 = new Location(62.2, 14.9, "Denmark");
            em.persist(destination2);


            // adding packages with shipments.
            package1.addShipment(shipment1);
            shipment1.addSource(source1);
            shipment1.addDestination(destination1);

            package1.addShipment(shipment4);
            shipment4.setSource(source1);
            shipment4.addDestination(destination2);

            package2.addShipment(shipment2);
            shipment2.addSource(destination1);
            shipment2.addDestination(source1);

            package3.addShipment(shipment3);
            shipment3.addSource(source1);
            shipment3.addDestination(destination2);

            em.getTransaction().commit();
        }*/
    }
}
