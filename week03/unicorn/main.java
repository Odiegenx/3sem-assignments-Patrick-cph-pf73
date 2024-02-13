package unicorn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class main {
    private static EntityManagerFactory emf1;

    public static void main(String[] args) {
        /*
        //HibernateConfig.getEntityManagerFactoryConfig();
        //EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        //var em = emf.createEntityManager();
        EntityManager em = emf.createEntityManager();
        Person steve = new Person("Maria","hej@email.dk", Person.Gender.MALE);
        //create(em,steve);
        Person person = em.find(Person.class,1);
        System.out.println(person);
         */
        //EntityManager em = emf.createEntityManager();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        UnicornDAO unicornDAO = UnicornDAO.getInstance(emf);
        Unicorn updateCorn = new Unicorn("PatrickWOOOH",9999,1337);
        int id1 = unicornDAO.save(new Unicorn("Patrick",999,1337)); // adds a new unicorn!
        int id2 = unicornDAO.save(new Unicorn("Niklas",21,420)); // adds another new unicorn!
        int id3 = unicornDAO.save(new Unicorn("Christian",-1,0)); // adds another new unicorn!
        int id4 = unicornDAO.save(new Unicorn("Rolin",6645,9999)); // adds another new unicorn!
        unicornDAO.update(updateCorn,id1);    // updates the first unicorn
        unicornDAO.delete(id2); // deletes the second unicorn.
        List<Unicorn> unicorns = unicornDAO.findAll();
        unicorns.forEach(System.out::println);
    }
    private static void create(EntityManager em, Person steve){
        em.getTransaction().begin();
        em.persist(steve);
        em.getTransaction().commit();
        em.close();
    }
}
