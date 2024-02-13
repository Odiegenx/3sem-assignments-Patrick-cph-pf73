package unicorn;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Table(name = "unicorn")
@Getter
@Setter
public class Unicorn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column( name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "power_strength")
    private int powerStrength;
    public Unicorn(String name, int age, int powerStrength) {
        this.name = name;
        this.age = age;
        this.powerStrength = powerStrength;
    }
    public Unicorn(){

    }

    @PrePersist
    public void prePersist(){
        this.name = name.toLowerCase();
    }
    @PreUpdate
    public void preUpdate(){
        this.name = name.toLowerCase();
    }
}
