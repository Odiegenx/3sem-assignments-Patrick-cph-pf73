package excerciseWithJavalinAndCrud.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomDTO {

    private int id;
    private int hotelId;
    private int number;
    private int price;

    public RoomDTO(){

    }
}
