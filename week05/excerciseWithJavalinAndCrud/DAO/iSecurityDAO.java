package excerciseWithJavalinAndCrud.DAO;

import java.util.Set;

public interface iSecurityDAO<U,R> {
    U createUser(String username,String password);
    R createRole(String role);
    void addRoleToUser(R r);
    Set<String> getRolesAsStrings();
    boolean verifyPassword(String pw);
    void removeRole(String role);
}
