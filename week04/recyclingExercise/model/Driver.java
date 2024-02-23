package recyclingExercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import recyclingExercise.config.HibernateConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Driver {
    @Id
    private String id;
    private BigDecimal salary;
    @Temporal(TemporalType.DATE)
    @Column(name = "employment_date")
    private Date employmentDate;
    private String name;
    private String surname;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private WasteTruck wasteTruck;

    public void addWasteTruck(WasteTruck truck){
        this.wasteTruck = truck;
        if(truck != null){
            truck.addDriver(this);
        }
    }

    public Driver(String name, String surname, BigDecimal salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @PrePersist
    public void prePersist(){
        Date currentDate = new Date();
        this.employmentDate = currentDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedEmploymentDate = dateFormat.format(this.employmentDate);
        StringBuilder tmpId = new StringBuilder();
        tmpId.append(formattedEmploymentDate.replace("-","").substring(2));
        tmpId.append("-");
        tmpId.append(name.charAt(0));
        tmpId.append(surname.charAt(0));
        tmpId.append("-");
        Random random = new Random();
        tmpId.append(random.nextInt(100,901));
        tmpId.append(surname.toUpperCase().charAt(surname.length()-1));
       if(validateDriverId(tmpId.toString())) {
            this.id = tmpId.toString();
        }
    }
    private Boolean validateDriverId(String driverId) {
        return driverId.matches("[0-9][0-9][0-9][0-9][0-9][0-9]-[A-Z][A-Z]-[0-9][0-9][0-9][A-Z]");
    }
}
