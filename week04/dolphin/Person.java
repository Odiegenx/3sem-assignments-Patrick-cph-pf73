package dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    // Relationer 1:1
    @OneToOne(mappedBy="person", cascade = CascadeType.ALL)
    private PersonDetail personDetail;

    public Person(String name)
    {
        this.name = name;
    }

    // Relationer 1:m
    // Fordi Person kan have mange Fees og det er person som ejer dem!
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Fee> fees = new HashSet<>();
    ///////// USER STORY 1
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PersonEvent> events = new HashSet<>();

    // relationer m:m
  /*  @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Event> events = new HashSet<>();

    // Uni-directional add
    public void addEvent(Event event){
        this.events.add(event);
    }
*/

    public void addEvent(Person person, Event event, LocalDate signupDate,int signupFee){
        PersonEvent personEvent = new PersonEvent(person,event,signupDate,signupFee);
        this.events.add(personEvent);
    }
    // Bi-directional update
    public void addPersonDetail(PersonDetail personDetail)
    {
        this.personDetail = personDetail;
        if (personDetail != null)
        {
            personDetail.setPerson(this);
        }
    }

    public void addFee(Fee fee)
    {
        this.fees.add(fee);
        if (fee != null)
        {
            fee.setPerson(this);
        }
    }

    ///////// USER STORY 1
    // sets up the bidirectional relationship between the note and the person holding it.
    public void addNote(Note note){
        this.notes.add(note);
        if(note != null){
            note.setCreatedBy(this);
        }
    }

}
