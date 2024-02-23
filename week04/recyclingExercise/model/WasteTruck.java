package recyclingExercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "waste_truck")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WasteTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private int capacity;
    @Column(name = "is_available")
    private boolean isAvailable;
    @Column(name = "registration_number")
    private String registrationNumber;
    @ToString.Exclude
    @OneToMany(mappedBy = "wasteTruck",fetch = FetchType.EAGER)
    private Set<Driver> drivers = new HashSet<>();

    public void addDriver(Driver driver){
        this.drivers.add(driver);
    }

    public WasteTruck(String brand, int capacity, String registrationNumber) {
        this.brand = brand;
        this.capacity = capacity;
        this.registrationNumber = registrationNumber;
    }
}
