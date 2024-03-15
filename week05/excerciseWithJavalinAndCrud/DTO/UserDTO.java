package excerciseWithJavalinAndCrud.DTO;

import excerciseWithJavalinAndCrud.secrurity.Role;
import excerciseWithJavalinAndCrud.secrurity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String Username;
    private String password;

    Set<Role> roles = new HashSet<>();

    public UserDTO(User user){
        this.Username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }
    public Set<String> getRolesAsString(){
        if(roles.isEmpty()){
            return null;
        }
        Set<String> rolesAsString = new HashSet<>();
        roles.forEach((role) -> {
            rolesAsString.add(role.getName());
        });
        return rolesAsString;
    }
}
