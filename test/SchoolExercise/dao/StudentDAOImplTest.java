package SchoolExercise.dao;

import SchoolExercise.Semester;
import SchoolExercise.Student;
import SchoolExercise.Teacher;
import SchoolExercise.config.HibernateConfig;
import SchoolExercise.dto.StudentInfo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOImplTest {
    static EntityManagerFactory emf;
    static StudentDAOImpl studentDAO;
    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        studentDAO = StudentDAOImpl.getInstance(emf);
    }
    @Test
    void findAllStudentsByFirstName() {
        List<Student> studentList = studentDAO.findAllStudentsByFirstName("Patrick");
        assertEquals(1,studentList.size());
        assertEquals("Patrick",studentList.get(0).getFirstName());
    }

    @Test
    void findAllStudentsByLastName() {
        List<Student> studentList = studentDAO.findAllStudentsByLastName("Jørgensen");
        assertEquals(1,studentList.size());
        assertEquals("Rolin",studentList.get(0).getFirstName());
    }

    @Test
    void findTotalNumberOfStudentsBySemester() {
        assertEquals(2,studentDAO.findTotalNumberOfStudentsBySemester("Datamatiker winter 2024"));
    }

    @Test
    void findTotalNumberOfStudentsByTeacher() {
        Teacher teacher = emf.createEntityManager().find(Teacher.class,1);
        assertEquals(3, studentDAO.findTotalNumberOfStudentsByTeacher(teacher));
    }

    @Test
    void findTeacherWithMostSemesters() {
        Teacher teacher = studentDAO.findTeacherWithMostSemesters();
        assertNotNull(teacher);
    }

    @Test
    void findSemesterWithFewestStudents(){
        Semester semester = studentDAO.findSemesterWithFewestStudents();
        assertNotNull(semester);
    }

    @Test
    void getAllStudentInfo() {
        StudentInfo studentInfo  = studentDAO.getAllStudentInfo(1);
        assertEquals("Patrick Hansen",studentInfo.getFullName());
    }
}