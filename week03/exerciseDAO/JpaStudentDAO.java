package exerciseDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lifeCycleAndAnnotationsJPA.Student;

import java.util.List;

public class JpaStudentDAO implements StudentDAO{
    private static EntityManagerFactory emf;
    private static JpaStudentDAO instance;

    public static JpaStudentDAO getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new JpaStudentDAO();
        }
        return instance;
    }
    @Override
    public void create(StudentDAOExercise student) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }
    }
    @Override
    public StudentDAOExercise read(int id) {
        try(EntityManager em = emf.createEntityManager()){
            // student is in managed state.
            StudentDAOExercise tmpS = em.find(StudentDAOExercise.class,id);
            return tmpS;
        }
    }
    @Override
    public List<StudentDAOExercise> readAll() {
        try(EntityManager em = emf.createEntityManager()){
            var query = em.createQuery("SELECT u from StudentDAOExercise u", StudentDAOExercise.class);
            return query.getResultList();
        }
    }
    @Override
    public StudentDAOExercise update(StudentDAOExercise student, int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Student tmpS = em.find(Student.class,id);
            if(tmpS != null){
                student.setId(tmpS.getId());
                StudentDAOExercise toMerge = em.merge(student);
                em.getTransaction().commit();
                return toMerge;
            }
            return null;
        }
    }
    @Override
    public void delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Student tmpS = em.find(Student.class,id);
            em.remove(tmpS);
            em.getTransaction().commit();
        }
    }
}
