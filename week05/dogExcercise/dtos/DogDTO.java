package dogExcercise.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DogDTO {

    private Integer id;
    private String name;
    private String breed;
    private String gender;
    private int age;
}
