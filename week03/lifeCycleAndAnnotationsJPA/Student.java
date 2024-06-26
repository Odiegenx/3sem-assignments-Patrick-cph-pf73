package lifeCycleAndAnnotationsJPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Setter
@Table(name = "students")
public class Student {
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
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;
    @Temporal(TemporalType.DATE)
    private LocalDate updatedAT;
    public Student(){

    }

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
    @PreUpdate
    public void preUpdate() {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.updatedAT = LocalDate.now();
        if (this.email.contains("@")) {
            this.email = email.toLowerCase();
        } else {
            throw new RuntimeException("Email was not valid");
        }

    }
    @PrePersist
    public void prePersist() {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.updatedAT = LocalDate.now();
        this.createdAt = LocalDate.now();
        if(this.email.contains("@")){
            this.email = email.toLowerCase();
        }else {
            throw new RuntimeException("Email was not valid");
        }
    }
}
