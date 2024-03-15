package excerciseWithJavalinAndCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*
    in the future there is no need to specify the hotelID as a field
    when the  room already have a hotel and a relation to it.
     */
    private Integer hotelId;
    private int number;
    private int price;

    public Room(Integer hotelId,int number,int price){
        this.hotelId = hotelId;
        this.number = number;
        this.price = price;
    }

    @ManyToOne(cascade = {CascadeType.REMOVE,CascadeType.DETACH})
    @JsonIgnore
    /*
    as mentioned above, instead you can choose the name of the column and join them
    with the annotiation below: (Not really needed as its mapped in Hotel).
     */
    //@JoinColumn(name = "hotelid")
    private Hotel hotel;

    @PrePersist
    public void prePersist(){
        if(this.hotelId == null) {
            this.hotelId = hotel.getId();
        }
    }
}
