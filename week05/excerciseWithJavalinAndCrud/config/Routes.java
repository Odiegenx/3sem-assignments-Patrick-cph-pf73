package excerciseWithJavalinAndCrud.config;

import excerciseWithJavalinAndCrud.controllers.HotelController;
import excerciseWithJavalinAndCrud.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    public static EndpointGroup setRoutes(){
        return () -> {
          path("/",getRoutes());
        };
    }
    /*Routing and Endpoints:

    In Javalin, routing refers to defining the paths that map HTTP requests
    to specific handlers or controllers.*/
    private static EndpointGroup getRoutes(){
        return  () ->{
            path("/hotels", () -> {
                    path("/rooms", () -> {
                        get("/", RoomController.getRooms());
                        get("/room/{id}", RoomController::getRoomById);
                        post("/room", RoomController::createRoom);
                        put("/room/{id}",RoomController::updateRoom);
                        delete("/room/{id}",RoomController::deleteRoom);
                    });
                get("/", HotelController.getHotels());
                get("/hotel/{id}",HotelController::getHotelById);
                get("/hotel/{id}/rooms",HotelController::getHotelRoomsByHotelId);
                post("/hotel",HotelController::createHotel);
                put("/hotel/{id}",HotelController::updateHotel);
                delete("/hotel/{id}",HotelController::deleteHotel);
            });
            get("/hello", ctx -> {
                String name = ctx.queryParam("name");
                ctx.result("Hello, " + name + "!");
            });
        };
    }
}
