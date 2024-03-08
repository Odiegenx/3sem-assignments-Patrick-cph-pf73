package dolphin.DAO;

import dolphin.DTO.AmountPaidDTO;
import dolphin.DTO.NoteWithNameAndAgeDTO;
import dolphin.Fee;
import dolphin.Note;
import dolphin.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JpaPersonDAO implements PersonDAO{
    private static EntityManagerFactory emf;
    private static JpaPersonDAO instance;

    public static JpaPersonDAO getInstance(EntityManagerFactory emf_){
        if(instance == null){
            emf = emf_;
            instance = new JpaPersonDAO();
        }
        return instance;
    }
    @Override
    public void create(Person person) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        }
    }
    @Override
    public Person read(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select p from Person p where id = ?1", Person.class)
                    .setParameter(1, id);
            return query.getSingleResult();
        }
    }
    @Override
    public Person update(Person person, int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Person> personTypedQuery = em.createQuery("select p from Person p where id =?1",Person.class)
                    .setParameter(1,id);
            Person tmpPerson = personTypedQuery.getSingleResult();
            if(tmpPerson != null){
                person.setId(tmpPerson.getId());
                person.getPersonDetail().setId(tmpPerson.getId());
                Person toMerge = em.merge(person);
                em.getTransaction().commit();
                return toMerge;
            }
            em.getTransaction().rollback();
            return null;
        }
    }
    @Override
    public void delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Person> personTypedQuery = em.createQuery("select p from Person p where id=?1", Person.class)
                    .setParameter(1,id);
            Person tmpPerson = personTypedQuery.getSingleResult();
            em.remove(tmpPerson);
            em.getTransaction().commit();
        }
    }
    @Override
    public List<Person> readAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Person> personTypedQuery = em.createQuery("SELECT p from Person p", Person.class);
            return personTypedQuery.getResultList();
        }
    }

   /* @Override
    public DAO<Person> getInstance(EntityManagerFactory emf_) {
        if(instance == null){
            emf = emf_;
            instance = new JpaPersonDAO();
        }
        return instance;
    }*/

    @Override
    public void addNoteToPerson(Note note, int id) {

    }
    @Override
    public void addFeeToPerson(Fee fee, int id) {

    }

    @Override
    public void deleteFeeFromPerson(int feeId, int personId) {

    }

    @Override
    public void deleteNoteFromPerson(int noteId, int personId) {

    }
    // USER STORY 2!
    @Override
    public AmountPaidDTO getToAmountPaid(int id) {
        Person person = read(id);
        int amountPaid = person.getFees().stream().mapToInt(x -> x.getAmount() ).sum();
        return new AmountPaidDTO(person.getName(),amountPaid,person.getFees());
    }
    // USER STORY 3!
    @Override
    public Set<Note> getNotesFromPerson(int id) {
        Person person = read(id);
        return person.getNotes();
    }
    // USER STORY 4!
    @Override
    public List<NoteWithNameAndAgeDTO> getNoteListWithNameAndAge() {
            List<Person> personList = readAll();
            List<NoteWithNameAndAgeDTO> noteWithNameAndAgeDTOS = personList.stream()
                    .flatMap(person -> person.getNotes()
                            .stream()
                            .map(note -> new NoteWithNameAndAgeDTO(note,person.getName(),person.getPersonDetail().getAge())))
                    .collect(Collectors.toCollection(ArrayList::new));
            return noteWithNameAndAgeDTOS;
        }
        public List<NoteWithNameAndAgeDTO> dtoProjection(){
            try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<NoteWithNameAndAgeDTO> query = em.createQuery(
                    "SELECT NEW dolphin.DTO.NoteWithNameAndAgeDTO(n, p.name, pd.age) " +
                            "FROM Note n " +
                            "JOIN n.createdBy p " +
                            "JOIN p.personDetail pd", NoteWithNameAndAgeDTO.class);
            return query.getResultList();
            }
        }
}
