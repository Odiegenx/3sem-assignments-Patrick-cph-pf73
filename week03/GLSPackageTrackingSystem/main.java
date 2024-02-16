package GLSPackageTrackingSystem;

import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        JpaPackageDAO jpaPackageDAO = JpaPackageDAO.getInstance(emf);
        Package package1 = Package.builder()
                .senderName("Patrick")
                .receiverName("Rolin")
                .trackingNumber("21")
                .deliveryStatus(Package.DeliveryStatus.PENDING)
                .build();
        Package package2 = Package.builder()
                .senderName("Rolin")
                .receiverName("Niklas")
                .trackingNumber("2556")
                .deliveryStatus(Package.DeliveryStatus.PENDING)
                .build();
        LocalDate now = LocalDate.now();
        Package package4 = new Package(999,"32ffa","SenderName","receivName", Package.DeliveryStatus.PENDING,now,now);

        // create:
        jpaPackageDAO.create(package1);
        jpaPackageDAO.create(package2);
        System.out.println("------------Read---------------");
        System.out.println(jpaPackageDAO.read("2556"));
        System.out.println("-----------Update------------");
        jpaPackageDAO.update(package4,"2556");
        System.out.println(jpaPackageDAO.read("32ffa"));
        System.out.println("---------Update Status----------");
        jpaPackageDAO.updateStatus(Package.DeliveryStatus.IN_TRANSIT, package1.getTrackingNumber());
        System.out.println(jpaPackageDAO.read(package1.trackingNumber));
        System.out.println(jpaPackageDAO.read(package4.trackingNumber));
    }
}
