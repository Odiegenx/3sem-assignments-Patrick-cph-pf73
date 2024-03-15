package excerciseWithJavalinAndCrud.secrurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @Column(name = "name",nullable = false)
    /*@Enumerated(EnumType.ORDINAL)
    private RoleType role;*/
    private String name;

    @ManyToMany(mappedBy ="roles",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    public Role(){

    }
    public Role(String name){
        this.name = name;
    }
}


