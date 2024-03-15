package excerciseWithJavalinAndCrud.secrurity;

import io.javalin.security.RouteRole;

public enum RoleType implements RouteRole {
        USER,
        ADMIN,
        ANYONE
}
