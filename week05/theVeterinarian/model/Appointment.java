package theVeterinarian.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    /*
    I was just practicing setting up entities, will most likely not use it for anything.
     */
    @Id
    private int id;
    private String description;
    private String date;

    public Appointment(int id,String description,LocalDate date,Patient patient){
        this.id = id;
        this.description = description;
        this.date = date.toString();
        this.patient = patient;
    }

    @ManyToOne
    private Patient patient;
  /*  public void setPatient(Patient patient) {
        if(patient != null) {
            this.patient = patient;
            patient.addAppointment(this);
        }
    }*/
}
