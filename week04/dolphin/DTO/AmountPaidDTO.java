package dolphin.DTO;

import dolphin.Fee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@ToString
@Getter
@Setter
public class AmountPaidDTO {
    String name;
    int amountPaid;
    Set<Fee> feeSet;

    public AmountPaidDTO(String name, int amountPaid, Set<Fee> feeSet) {
        this.name = name;
        this.amountPaid = amountPaid;
        this.feeSet = feeSet;
    }
}
