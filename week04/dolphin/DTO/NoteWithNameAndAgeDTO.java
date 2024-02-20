package dolphin.DTO;

import dolphin.Note;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class NoteWithNameAndAgeDTO {
    Note note;
    String name;
    int age;

    public NoteWithNameAndAgeDTO(Note note, String name, int age) {
        this.note = note;
        this.name = name;
        this.age = age;
    }
}
