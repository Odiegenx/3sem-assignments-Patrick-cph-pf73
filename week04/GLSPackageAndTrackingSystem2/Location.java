package GLSPackageAndTrackingSystem2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "location")
@NoArgsConstructor
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String address;

    public Location(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "destination")
    private List<Shipment> shipmentDestinationlist = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "source")
    private List<Shipment> shipmentSourceList = new ArrayList<>();

    public void addShipmentToSourceList(Shipment shipment){
        if(shipment != null && !shipmentSourceList.contains(shipment)){
            shipmentSourceList.add(shipment);
        }
    }
    public void addShipmentToDestinationList(Shipment shipment){
        if(shipment != null && !shipmentDestinationlist.contains(shipment)){
            shipmentDestinationlist.add(shipment);
        }
    }

}