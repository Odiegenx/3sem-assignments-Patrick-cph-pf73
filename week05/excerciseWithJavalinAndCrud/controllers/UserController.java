package excerciseWithJavalinAndCrud.controllers;

import excerciseWithJavalinAndCrud.DAO.RoleDAO;
import excerciseWithJavalinAndCrud.DAO.UserDAO;
import excerciseWithJavalinAndCrud.DTO.TokenDTO;
import excerciseWithJavalinAndCrud.DTO.UserDTO;
import excerciseWithJavalinAndCrud.Exception.ApiException;
import excerciseWithJavalinAndCrud.Exception.UserNameInUseException;
import excerciseWithJavalinAndCrud.secrurity.Role;
import excerciseWithJavalinAndCrud.secrurity.User;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.hibernate.metamodel.internal.AbstractPojoInstantiator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {

    private static UserDAO userDAO;
    public UserController(boolean isTest){
        userDAO = UserDAO.getInstance(isTest);
    }

    public static User getVerifiedUser(User user){
        return userDAO.verifyUser(user.getUsername(), user.getPassword());
    }

    public static void registerUser(Context ctx) throws UserNameInUseException {
        ctx.bodyValidator(User.class);
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        User toFind = userDAO.getById(userDTO.getUsername());
        if(toFind != null){
            ctx.status(400);
            throw new UserNameInUseException("User name is already in use");
        }
        Set<Role> roles = new HashSet<>();
        Role role = null;
        for (Role r:userDTO.getRoles()) {
            if(r.getName().equals("user")){
                role = RoleDAO.getInstance(false).getById("1");
            }
            if(r.getName().equals("admin")){
                role = RoleDAO.getInstance(false).getById("2");
            }
            roles.add(role);
        }
        userDTO.setRoles(new HashSet<>());
        User user = new User(userDTO);
        user.addRole(role);
        /*List<Role> roles = RoleDAO.getInstance(false).getAll();
        if (roles != null && !roles.isEmpty()) {
            Set<Role> newUserRoles = new HashSet<>();
            for (Role r : roles) {
                if (user.getRoles().contains(r)) {
                    newUserRoles.add(r);
                }
            }
            if(!newUserRoles.isEmpty()) {
                user.setRoles(newUserRoles);
            }
        }*/
        userDAO.create(user);
        try {
             String token = SecurityController.createToken(userDTO);
            ctx.json(new TokenDTO(token,user.getUsername()));
        }catch (ApiException e){
            ctx.status(e.getStatusCode());
            ctx.result(e.getMessage());
        }
    }

    public static void login(Context ctx) {
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        User user = new User(userDTO);
        User userVerified = UserController.getVerifiedUser(user);

        if(userVerified!= null) {
            try {
                String token = SecurityController.createToken(new UserDTO(userVerified));
                ctx.json(new TokenDTO(token,user.getUsername()));
            }catch (ApiException e){
                ctx.status(e.getStatusCode());
                ctx.result(e.getMessage());
            }
        }
    }
}
