package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.secrurity.Role;
import excerciseWithJavalinAndCrud.secrurity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;

public class UserDAO extends DAO<User,String> implements iSecurityDAO<User,Role> {

    private static UserDAO instance;

    public UserDAO(boolean isTest){
        super(User.class,isTest);
    }
    public static UserDAO getInstance(boolean isTest){
        if(instance == null){
            instance = new UserDAO(isTest);
        }
        return instance;
    }
    public User createUser(String username, String password) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            User user = new User(username,password);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }
    public User verifyUser(String username,String password){
        EntityManager em = emf.createEntityManager();
        User userToFind = em.find(User.class,username);
        if(userToFind == null) throw new EntityNotFoundException("No user with username: " + username +" was found");
        if(!userToFind.verifyUser(password)){
            throw new EntityNotFoundException("Wrong password");
        }
        return userToFind;
    }

    @Override
    public Role createRole(String role) {
        return null;
    }

    @Override
    public void addRoleToUser(Role role) {
    }

    @Override
    public Set<String> getRolesAsStrings() {
        return null;
    }

    @Override
    public boolean verifyPassword(String pw) {
        return false;
    }

    @Override
    public void removeRole(String role) {

    }

}
