package dolphin.DAO;
import dolphin.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JpaPersonDAOTest {
    static EntityManagerFactory emf;
    static EntityManager em;
    static JpaPersonDAO jpaPackageDAO;
    static Person basePerson1;
    static Person basePerson2;
    static Person testPerson;

    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryConfigTEST();
        jpaPackageDAO = JpaPersonDAO.getInstance(emf);
        Person testP = new Person("Frygtløs");
        PersonDetail pd2 = new PersonDetail("testmedtestpå", 9999, "IntetSted", 4);
        Fee testF1 = new Fee(125, LocalDate.now());
        Fee testF2 = new Fee(125, LocalDate.now());
        Note testN1 = new Note("remember to not be afraid", LocalDate.of(2024,03,31));
        Note testN2 = new Note("remember: old lady nice, old man not nice",LocalDate.of(2024,04,05));
        testP.addPersonDetail(pd2);
        testP.addFee(testF1);
        testP.addFee(testF2);
        testP.addNote(testN1);
        testP.addNote(testN2);
        testPerson = testP;
    }
    @BeforeEach
    void setUp() {
        try(EntityManager em = emf.createEntityManager()) {
            basePerson1 = new Person("Hanzi");
            PersonDetail pd1 = new PersonDetail("Algade 2", 4300, "Holbæk", 45);
            basePerson1.addPersonDetail(pd1);
            Fee f1 = new Fee(125, LocalDate.now());
            Fee f2 = new Fee(125, LocalDate.now());
            Note n1 = new Note("remember birthday", LocalDate.of(2024, 03, 31));
            Note n2 = new Note("make new lesson plan", LocalDate.of(2024, 04, 05));
            basePerson1.addFee(f1);
            basePerson1.addFee(f2);
            basePerson1.addNote(n1);
            basePerson1.addNote(n2);

            basePerson2 = new Person("Zoom");
            PersonDetail pd2 = new PersonDetail("Aloover", 43320, "Rungsted", 58);
            basePerson2.addPersonDetail(pd2);
            Fee f3 = new Fee(125, LocalDate.now());
            Fee f4 = new Fee(125, LocalDate.now());
            Note n3 = new Note("remember birthday", LocalDate.of(2024, 03, 31));
            Note n4 = new Note("make new lesson plan", LocalDate.of(2024, 04, 05));
            basePerson2.addFee(f3);
            basePerson2.addFee(f4);
            basePerson2.addNote(n3);
            basePerson2.addNote(n4);
            em.getTransaction().begin();
            em.createQuery("delete from Fee").executeUpdate();
            em.createQuery("delete from Note").executeUpdate();
            em.createQuery("delete from PersonDetail ").executeUpdate();
            em.createQuery("delete from Person").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1").executeUpdate();
            em.persist(basePerson1);
            em.persist(basePerson2);
            em.getTransaction().commit();
        }
    }
    @AfterAll
    static void afterAll(){
       /* EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Fee").executeUpdate();
        entityManager.createQuery("delete from Note").executeUpdate();
        entityManager.createQuery("delete from PersonDetail ").executeUpdate();
        entityManager.createQuery("delete from Person").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE Note_id_seq RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE Fee_id_seq RESTART WITH 1").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();*/
        emf.close();
    }
    @Test
    void create(){
        jpaPackageDAO.create(testPerson);
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select p from Person p where name = ?1", Person.class)
                    .setParameter(1, "Frygtløs");
            assertEquals("Frygtløs", query.getSingleResult().getName());
        }
    }
    @Test
    void read() {
        jpaPackageDAO.read(basePerson1.getId());
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select p from Person p where id = ?1", Person.class)
                    .setParameter(1, basePerson1.getId());
            assertEquals("Hanzi", query.getSingleResult().getName());
        }
    }

    @Test
    void update() {
        assertEquals("Zoom",basePerson2.getName());
        Person updatedPerson = jpaPackageDAO.update(testPerson,basePerson2.getId());
        assertEquals("Frygtløs",updatedPerson.getName());
    }

    @Test
    void delete() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select p from Person p", Person.class);
            assertEquals(2, query.getResultList().size());
            jpaPackageDAO.delete(2);
            TypedQuery<Person> query1 = em.createQuery("select p from Person p", Person.class);
            assertEquals(1, query.getResultList().size());
        }
    }

    @Test
    void readAll() {
    }
}