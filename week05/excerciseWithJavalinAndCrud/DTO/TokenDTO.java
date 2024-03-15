package excerciseWithJavalinAndCrud.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String token;
    private String userName;

    public TokenDTO(String token,String userName){
        this.token = token;
        this.userName = userName;
    }
}
