package schoolExercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "student_school")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;

    @ManyToOne
    private Semester currentSemester;

    public Student(String firstName,String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void addSemester(Semester semester){
        if(semester != null && !semester.getStudents().contains(this)) {
            this.currentSemester = semester;
            semester.addStudent(this);
        }else{
            // throw Student already enrolledException (need to make it)
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id= " + id +
                ", name= " + firstName+" "+lastName +
                ", age= " + age +
                ", currentSemester= " + currentSemester.getName() +
                ", currentSemester description= "+currentSemester.getDescription()+
                '}';
    }
}