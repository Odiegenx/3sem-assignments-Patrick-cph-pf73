package GLSPackageTrackingSystem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import GLSPackageTrackingSystem.Package;

class JpaPackageDAOTest {
    static EntityManagerFactory emf;
    static EntityManager em;
    static JpaPackageDAO jpaPackageDAO;
    Package package1 = Package.builder()
            .senderName("Cads")
            .receiverName("Hans")
            .trackingNumber("2453rr")
            .deliveryStatus(Package.DeliveryStatus.PENDING)
            .build();
    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        em = emf.createEntityManager();
        jpaPackageDAO = JpaPackageDAO.getInstance(emf);
    }
    @AfterAll
    static void afterAll(){
        emf.close();
        em.close();
    }

    @Test
    void create() {
        jpaPackageDAO.create(package1);
    }

    @Test
    void read() {
        package1 = jpaPackageDAO.read(package1.trackingNumber);
        assertEquals(package1,jpaPackageDAO.read(package1.trackingNumber));
    }

    @Test
    void updateStatus() {
        assertEquals(Package.DeliveryStatus.IN_TRANSIT,jpaPackageDAO.updateStatus(Package.DeliveryStatus.IN_TRANSIT,package1.getTrackingNumber())
                .getDeliveryStatus());
    }
    @Test
    void readAll() {
        assertEquals(3,jpaPackageDAO.readAll().size());
    }
    @Test
    void delete() {
        jpaPackageDAO.delete(package1.getTrackingNumber());
        assertEquals(0,jpaPackageDAO.readAll().size());
    }
}