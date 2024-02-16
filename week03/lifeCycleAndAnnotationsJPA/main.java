package lifeCycleAndAnnotationsJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class main {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    public static void main(String[] args) {
        // entity student is in transient state.
        try {
            Student patrick = new Student("Patrick", "Fabrin", "Hej@hotmail.com", 35);
            Student patrick2 = new Student("Patrick", "Fabrin", "Hejhotmail.com", 35);
            createStudent(patrick);
            createStudent(patrick2);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        readAllStudents().forEach(System.out::println);
    }

    public static void createStudent(Student student){
        try(EntityManager em = emf.createEntityManager()) {
            // student is in transient state before persist.
            em.getTransaction().begin();
            // student is in managed state after persist.
            em.persist(student);
            // student is detached after the commit.
            em.getTransaction().commit();
        }
    }
    public static Student readStudent(int id){
        try(EntityManager em = emf.createEntityManager()){
            // student is in managed state.
            Student tmpS = em.find(Student.class,id);
            return tmpS;
        }
    }
    public static Student updateStudent(Student updStd, int id){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            // student is in managed state if any is found.
            Student tmpS = em.find(Student.class,id);
            if(tmpS != null){
                updStd.setId(tmpS.getId());
                Student toMerge = em.merge(updStd);
                // student is in detached sate after commit.
                em.getTransaction().commit();
                return toMerge;
            }
            return null;
        }
    }
    public static void deleteStudent(int id){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            // student in managed state.
            Student tmpS = em.find(Student.class,id);
            em.remove(tmpS);
            // student is in removed state.
            em.getTransaction().commit();
        }
    }
    public static List<Student> readAllStudents(){
        try(EntityManager em = emf.createEntityManager()){
            // students are in managed state.
            var quary = em.createQuery("select s from Student s", Student.class);
            return quary.getResultList();
        }
    }
}
