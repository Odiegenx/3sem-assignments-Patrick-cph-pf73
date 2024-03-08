package dolphin.DAO;

import dolphin.DTO.AmountPaidDTO;
import dolphin.DTO.NoteWithNameAndAgeDTO;
import dolphin.Fee;
import dolphin.Note;
import dolphin.Person;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

public interface PersonDAO extends DAO<Person> {
    public void addNoteToPerson(Note note, int id);
    public void addFeeToPerson(Fee fee,int id);
    public void deleteFeeFromPerson(int feeId,int personId);
    public void deleteNoteFromPerson(int noteId,int personId);
    // FOR IMPLEMENTING USER STORY 2
    public AmountPaidDTO getToAmountPaid(int id);
    // FOR IMPLEMENTING USER STORY 3
    public Set<Note> getNotesFromPerson(int id);
    // FOR IMPLEMENTING USER STORY 4
    public List<NoteWithNameAndAgeDTO> getNoteListWithNameAndAge();
}
