package excerciseWithJavalinAndCrud.config;

import excerciseWithJavalinAndCrud.controllers.HotelController;
import excerciseWithJavalinAndCrud.controllers.RoomController;
import excerciseWithJavalinAndCrud.controllers.SecurityController;
import excerciseWithJavalinAndCrud.controllers.UserController;
import excerciseWithJavalinAndCrud.secrurity.RoleType;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    public static EndpointGroup setRoutes(){
        return () -> {
            before(SecurityController::authenticate);
          path("/hotels",getRoutes());
          path("/hotels",getSecrurityRoutes());
          path("/hotels",getProtectedRoutes());
        };
    }
    /*Routing and Endpoints:

    In Javalin, routing refers to defining the paths that map HTTP requests
    to specific handlers or controllers.*/
    private static EndpointGroup getRoutes(){
        return  () ->{
            path("/rooms", () -> {
                get("/", RoomController.getRooms(),RoleType.ADMIN,RoleType.USER);
                get("/room/{id}", RoomController::getRoomById,RoleType.ADMIN,RoleType.USER);
                post("/room", RoomController::createRoom,RoleType.ADMIN);
                put("/room/{id}",RoomController::updateRoom,RoleType.ADMIN);
                delete("/room/{id}",RoomController::deleteRoom,RoleType.ADMIN);
            });
            get("/", HotelController.getHotels(),RoleType.ADMIN,RoleType.USER);
            get("/hotel/{id}",HotelController::getHotelById,RoleType.USER,RoleType.ADMIN);
            get("/hotel/{id}/rooms",HotelController::getHotelRoomsByHotelId,RoleType.USER,RoleType.ADMIN);
            post("/hotel",HotelController::createHotel,RoleType.ADMIN);
            put("/hotel/{id}",HotelController::updateHotel,RoleType.ADMIN);
            delete("/hotel/{id}",HotelController::deleteHotel,RoleType.ADMIN);
            get("/hello", ctx -> {
                String name = ctx.queryParam("name");
                ctx.result("Hello, " + name + "!");
            });
        };
    }
    private static EndpointGroup getSecrurityRoutes() {
        return () -> {
            path("/auth", () -> {
               // before(SecurityController::authenticate);
                post("/register", UserController::registerUser, RoleType.ANYONE);
                post("/login",UserController::login, RoleType.ANYONE);
            });
        };
    }
    private static EndpointGroup getProtectedRoutes(){
        return () -> {
          path("/protected",() ->{
             // before(SecurityController::authenticate);
              get("/user", ctx -> ctx.result("great success from User Route"),RoleType.USER);
              get("/admin", ctx -> ctx.result("great success from Admin Route"),RoleType.ADMIN);
          });
        };
    }
}
