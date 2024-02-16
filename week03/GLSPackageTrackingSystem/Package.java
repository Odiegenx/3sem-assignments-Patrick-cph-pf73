package GLSPackageTrackingSystem;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.EnumMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "package")
//@Data
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "tracking_number",nullable = false)
    String trackingNumber;
    @Column(name = "sender_name")
    String senderName;
    @Column(name = "receiver_name")
    String receiverName;
    @Enumerated(EnumType.STRING)
    @Column(name="delivery_status")
    DeliveryStatus deliveryStatus;
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;
    @Temporal(TemporalType.DATE)
    private LocalDate updatedAT;

    @PreUpdate
    public void preUpdate() {
        this.senderName = senderName.toLowerCase();
        this.receiverName = receiverName.toLowerCase();
        this.updatedAT = LocalDate.now();
    }
    @PrePersist
    public void prePersist() {
        this.senderName = senderName.toLowerCase();
        this.receiverName = receiverName.toLowerCase();
        this.updatedAT = LocalDate.now();
        this.createdAt = LocalDate.now();
    }

    public enum DeliveryStatus{
        PENDING,
        IN_TRANSIT,
        DELIVERY
    }
}
