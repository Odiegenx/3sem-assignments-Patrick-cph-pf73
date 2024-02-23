package GLSPackageAndTrackingSystem2;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "package2")
//@Data
public class Package2 {
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

    @OneToMany(mappedBy = "package2",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Shipment> shipments = new ArrayList<>();

    public Package2(String trackingNumber, String senderName, String receiverName, DeliveryStatus deliveryStatus, LocalDate createdAt, LocalDate updatedAT) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
        this.createdAt = createdAt;
        this.updatedAT = updatedAT;
    }

    public void addShipment(Shipment shipment){
        if(shipment != null){
            shipments.add(shipment);
            shipment.setPackage2(this);
        }
    }
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

