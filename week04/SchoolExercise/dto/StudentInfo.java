package SchoolExercise.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StudentInfo {
    String fullName;
    int studentID;
    String thisSemesterName;
    String thisSemesterDescription;

    public StudentInfo(String fullName, int studentID, String thisSemesterName, String thisSemesterDescription) {
        this.fullName = fullName;
        this.studentID = studentID;
        this.thisSemesterName = thisSemesterName;
        this.thisSemesterDescription = thisSemesterDescription;
    }
}
