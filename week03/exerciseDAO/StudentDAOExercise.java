package exerciseDAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@Table(name = "studentdaoexercise")
public class StudentDAOExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "age")
    private int age;
    public StudentDAOExercise(){

    }

    public StudentDAOExercise(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
    @PreUpdate
    public void preUpdate() {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        if(this.email.contains("@")){
            this.email = email.toLowerCase();
        }else {
            throw new RuntimeException("Email was not valid");
        }
    }
    @PrePersist
    public void prePersist() {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        if(this.email.contains("@")){
            this.email = email.toLowerCase();
        }else {
            throw new RuntimeException("Email was not valid");
        }
    }
}
