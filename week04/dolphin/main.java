package dolphin;

import dolphin.DAO.JpaPersonDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        JpaPersonDAO jpaPersonDAO = JpaPersonDAO.getInstance(emf);
        Person person = jpaPersonDAO.read(1);
        System.out.println(person.getName());
        Set<Fee> fees = person.getFees();
        fees.forEach(System.out::println);
        System.out.println(person);
        // USER STORY 2!
        System.out.println(jpaPersonDAO.getToAmountPaid(1));
        // USER STORY 3!
        System.out.println(jpaPersonDAO.getNotesFromPerson(1));
        // USER STORY 4!
        jpaPersonDAO.getNoteListWithNameAndAge().forEach(System.out::println);

/*        try(EntityManager em = emf.createEntityManager()) {
            Person p1 = new Person("Hanzi");
            PersonDetail pd1 = new PersonDetail("Algade 2", 4300, "Holbæk", 45);
            p1.addPersonDetail(pd1);
            Fee f1 = new Fee(125, LocalDate.now());
            Fee f2 = new Fee(125, LocalDate.now());
            Note n1 = new Note("remember birthday", LocalDate.of(2024,03,31));
            Note n2 = new Note("make new lesson plan",LocalDate.of(2024,04,05));
            p1.addFee(f1);
            p1.addFee(f2);
            p1.addNote(n1);
            p1.addNote(n2);
            em.getTransaction().begin();
            em.persist(p1);
            //Person person = em.find(Person.class,1);
            //em.remove(person);
            em.getTransaction().commit();
        }*/
    }
}
