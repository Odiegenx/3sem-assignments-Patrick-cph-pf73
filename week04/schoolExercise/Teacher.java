package schoolExercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Semester> teaches = new ArrayList<>();

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSemester(Semester semester){
        if(semester != null && !teaches.contains(semester)){
            teaches.add(semester);
            if(!semester.getTeachers().contains(this)){
                semester.addTeacher(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", teaches=" + teaches +
                '}';
    }
}