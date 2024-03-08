package theVeterinarian.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Patient {
    /*
      I was just practicing setting up entities, will most likely not use it for anything.
       */
    @Id
    private int id;
    private String name;

    public Patient(int id,String name){
        this.id = id;
        this.name = name;
    }

   /* @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment){
        this.appointments.add(appointment);
        if(appointment.getPatient() == null){
            appointment.setPatient(this);
        }
    }*/
}
