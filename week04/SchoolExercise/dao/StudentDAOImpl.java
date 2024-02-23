package SchoolExercise.dao;

import SchoolExercise.Semester;
import SchoolExercise.Student;
import SchoolExercise.Teacher;
import SchoolExercise.dto.StudentInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDAOImpl implements StudentDAO{

    private static EntityManagerFactory emf;
    private static StudentDAOImpl instance;

    public static StudentDAOImpl getInstance(EntityManagerFactory emf_){
        if(instance == null){
            emf = emf_;
            instance = new StudentDAOImpl();
        }
        return instance;
    }

    @Override
    public List<Student> findAllStudentsByFirstName(String firstName) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> studentTypedQuery = em.createQuery("select s from SchoolExercise.Student s where s.firstName like concat(?1,'%')",Student.class)
                    .setParameter(1,firstName);
            return studentTypedQuery.getResultList();
        }
    }

    @Override
    public List<Student> findAllStudentsByLastName(String lastName) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> studentTypedQuery = em.createQuery("select s from SchoolExercise.Student s where s.lastName like concat(?1,'%')",Student.class)
                    .setParameter(1,lastName);
            return studentTypedQuery.getResultList();
        }
    }

    @Override
    public long findTotalNumberOfStudentsBySemester(String semesterName) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Long> longTypedQuery = em.createQuery("select count(s) from SchoolExercise.Student s where s.currentSemester.name = ?1",Long.class)
                    .setParameter(1,semesterName);
            return longTypedQuery.getSingleResult();
        }
    }

    @Override
    public long findTotalNumberOfStudentsByTeacher(Teacher teacher) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Long> longTypedQuery = em.createQuery("select count(s) from SchoolExercise.Student s join s.currentSemester.teachers t where t =?1",Long.class)
                    .setParameter(1,teacher);
            return longTypedQuery.getSingleResult();
        }
    }

    @Override
    public Teacher findTeacherWithMostSemesters() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Teacher> teacherTypedQuery = em.createQuery("select t from Teacher t order by size(teaches) desc", Teacher.class);
            teacherTypedQuery.setMaxResults(1);
            return teacherTypedQuery.getSingleResult();
        }
    }

    @Override
    public Semester findSemesterWithFewestStudents() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Semester> semesterTypedQuery = em.createQuery("select s from Semester s order by size(s.students) asc", Semester.class);
            semesterTypedQuery.setMaxResults(1);
            return semesterTypedQuery.getSingleResult();
        }
    }

    @Override
    public StudentInfo getAllStudentInfo(int id) {
            try(EntityManager em = emf.createEntityManager()) {
                TypedQuery<StudentInfo> query = em.createQuery(
                        "SELECT NEW SchoolExercise.dto.StudentInfo(concat(s.firstName,' ',s.lastName),s.id,cs.name,cs.description) FROM SchoolExercise.Student s JOIN s.currentSemester cs where s.id =?1", StudentInfo.class)
                        .setParameter(1,id);
                return query.getSingleResult();
            }
    }
}
