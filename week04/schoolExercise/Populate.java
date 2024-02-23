package schoolExercise;

import schoolExercise.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

        try(EntityManager em = emf.createEntityManager();) {
            em.getTransaction().begin();

            Student student1 = new Student("Patrick","Hansen",26);
            em.persist(student1);
            Student student2 = new Student("Niklas","Jensen",22);
            em.persist(student2);
            Student student3 = new Student("Rolin","Jørgensen",45);
            em.persist(student3);
            Student student4 = new Student("Christian","Thomasen",99);
            em.persist(student4);

            Semester semester1 = new Semester("Datamatiker winter 2024","learn programming, quick and easily!");
            em.persist(semester1);

            Semester semester2 = new Semester("Gymnastics Fall 2024","For those that likes to run around!");
            em.persist(semester2);
            Semester semester3 = new Semester("English Fall 2024","English for dummies");
            em.persist(semester3);

            Teacher teacher1 = new Teacher("Hanzi","Hinterseer");
            em.persist(teacher1);

            Teacher teacher2 = new Teacher("Klaus","Larsen");
            em.persist(teacher2);

            semester1.addStudent(student1);
            semester1.addStudent(student2);
            semester2.addStudent(student3);
            semester3.addStudent(student4);

            teacher1.addSemester(semester1);
            teacher1.addSemester(semester3);
            teacher2.addSemester(semester2);

            em.getTransaction().commit();
        }
    }
}
   /*StudentDAOImpl studentDAO = StudentDAOImpl.getInstance(emf);
        studentDAO.findAllStudentsByFirstName("Patrick").forEach(System.out::println);
        studentDAO.findAllStudentsByLastName("Jørgensen").forEach(System.out::println);
        System.out.println(studentDAO.findTotalNumberOfStudentsBySemester("Datamatiker winter 2024"));
        Teacher teacher = emf.createEntityManager().find(Teacher.class,1);
        System.out.println(studentDAO.findTotalNumberOfStudentsByTeacher(teacher));
        System.out.println(studentDAO.findTeacherWithMostSemesters());
        System.out.println(studentDAO.findSemesterWithFewestStudents());
        System.out.println(studentDAO.getAllStudentInfo(1));*/