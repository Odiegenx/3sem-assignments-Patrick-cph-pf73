package exerciseDAO;

import jakarta.persistence.EntityManagerFactory;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        JpaStudentDAO jpaStudentDAO = JpaStudentDAO.getInstance(emf);
        try {
            StudentDAOExercise patrick = new StudentDAOExercise("Patrick", "Fabrin", "Hej@hotmail.com", 35);
            StudentDAOExercise patrick2 = new StudentDAOExercise("Patrick", "Fabrin", "Hejhotmail.com", 35);
            jpaStudentDAO.create(patrick);
            jpaStudentDAO.create(patrick2);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        System.out.println(jpaStudentDAO.read(1));
    }
}
