package dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Name;
    private LocalDate date;

    @ToString.Exclude
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private Set<PersonEvent> persons = new HashSet<>();

    public Event(String name, LocalDate date) {
        Name = name;
        this.date = date;
    }
}
