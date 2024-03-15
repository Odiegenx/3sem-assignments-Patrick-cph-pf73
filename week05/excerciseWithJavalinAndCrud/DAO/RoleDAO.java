package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.model.Room;
import excerciseWithJavalinAndCrud.secrurity.Role;

public class RoleDAO extends DAO<Role,String>{
    private static RoleDAO instance;
    private RoleDAO(boolean isTest) {
        super(Role.class,isTest);
    }

    public static RoleDAO getInstance(boolean isTest){
        if(instance == null){
            instance = new RoleDAO(isTest);
        }
        return instance;
    }
}
