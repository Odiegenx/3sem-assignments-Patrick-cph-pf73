package GLSPackageAndTrackingSystem2;

import GLSPackageAndTrackingSystem2.Location;
import GLSPackageAndTrackingSystem2.Package2;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "shipment")
@ToString
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private LocalDate shipmentDate;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},optional = false)
    private Package2 package2;
    @ManyToOne(cascade = CascadeType.ALL)
    private Location source;
    @ManyToOne(cascade = CascadeType.ALL)
    private Location destination;

    public Shipment(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
    public void addPackage2(Package2 package2){
        if(package2 != null){
            this.package2 = package2;
            if(!package2.getShipments().contains(this)) {
                package2.addShipment(this);
            }
        }
    }
    public void addSource(Location location){
        if(location != null) {
            this.source = location;
            location.addShipmentToSourceList(this);
        }
    }
    public void addDestination(Location location){
        if(location != null){
            this.destination = location;
            location.addShipmentToDestinationList(this);
        }
    }
    @PrePersist
    public void prePersist() {
        this.shipmentDate = LocalDate.now();
    }
}