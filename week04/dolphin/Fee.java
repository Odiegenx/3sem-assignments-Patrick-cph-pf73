package dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int amount;
    private LocalDate payDate;

    // fordi at en person kan have mange fees!
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;
    public Fee(int amount, LocalDate payDate) {
        this.amount = amount;
        this.payDate = payDate;
    }

}
