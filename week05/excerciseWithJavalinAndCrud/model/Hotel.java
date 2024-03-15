package excerciseWithJavalinAndCrud.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms = new ArrayList<>();

    public Hotel(String name,String address){
        this.name = name;
        this.address = address;
    }
    public Hotel(String name,String address,List<Room> rooms_){
        this.name = name;
        this.address = address;
        this.rooms = rooms_;
    }


    public void addRoom(Room room){
        if(room != null) {
            room.setHotelId(this.id);
            room.setHotel(this);
            rooms.add(room);
        }
    }

}
