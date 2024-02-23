package dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class PersonDetail {
    @Id
    private Integer id;
    private String address;
    private int zip;
    private String city;
    private int age;

    // Relationer 1:1
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId// means it should take the persons id and use it as an id in PersonDetail
    private Person person;

    public PersonDetail(String address, int zip, String city, int age) {
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.age = age;
    }
}


