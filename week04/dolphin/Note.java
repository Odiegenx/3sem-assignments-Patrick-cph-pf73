package dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
///////// USER STORY 1
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String note;
    private LocalDate created;

    @ToString.Exclude
    @ManyToOne
    private Person createdBy;

    public Note(String note, LocalDate created) {
        this.note = note;
        this.created = created;
    }
}
