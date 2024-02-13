package unicorn;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "person")
@Getter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public Person(String firstName, String email, Gender gender) {
        this.firstName = firstName;
        this.email = email;
        this.gender = gender;
    }
    public Person(){

    }
    public enum  Gender {
        MALE,
        FEMALE
    }
}
