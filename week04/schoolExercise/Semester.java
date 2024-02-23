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
@Table(name = "semester")
@NoArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "currentSemester",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();
    @ManyToMany(mappedBy = "teaches", fetch = FetchType.EAGER)
    private List<Teacher> teachers = new ArrayList<>();

    public Semester(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addStudent(Student student){
        if(student != null && !students.contains(student)) {
            this.students.add(student);
            //if(!student.currentSemester.equals(this)) {
                student.setCurrentSemester(this);
            //}
        }
    }
    public void addTeacher(Teacher teacher){
        if(teacher != null && !teachers.contains(teacher)){
            teachers.add(teacher);
            if(!teacher.getTeaches().contains(this)){
                teacher.getTeaches().add(this);
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Semester{");
        sb.append("name=").append(name);
        sb.append(", description=").append(description);

        sb.append(", students= ");
        if (!students.isEmpty()) {
            for (Student student : students) {
                sb.append(student.getFirstName());
                sb.append(" ");
                sb.append(student.getLastName());
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
        }

        sb.append(", teachers= ");
        if (!teachers.isEmpty()) {
            for (Teacher teacher : teachers) {
                sb.append(teacher.getFirstName());
                sb.append(" ");
                sb.append(teacher.getLastName());
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

}