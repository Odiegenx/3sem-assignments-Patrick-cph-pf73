package excerciseWithJavalinAndCrud.secrurity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import excerciseWithJavalinAndCrud.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name ="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String Username;
    private String password;

    /*@JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_name",referencedColumnName = "username")},inverseJoinColumns = {
            @JoinColumn(name = "role_name",referencedColumnName = "name")})*/
    @ManyToMany(/*cascade = CascadeType.PERSIST,*/fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String username,String password){
        this.Username=username;
        this.password= password;
    }
    public User(UserDTO userDTO){

        this.Username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.roles = userDTO.getRoles();
    }
    public void addRole(Role role){
        roles.add(role);
        role.getUsers().add(this);
    }
    private void removeRole(Role role){
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @JsonIgnore
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
    public boolean verifyUser(String password){
        return BCrypt.checkpw(password,this.password);
    }

    @PrePersist
    public void prePersist(){
        String salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password,salt);
    }
}
