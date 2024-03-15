package excerciseWithJavalinAndCrud.controllers;

import excerciseWithJavalinAndCrud.DAO.HotelDAO;
import excerciseWithJavalinAndCrud.DAO.RoomDAO;
import excerciseWithJavalinAndCrud.DTO.HotelDTO;
import excerciseWithJavalinAndCrud.DTO.RoomDTO;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomController {

    private static RoomDAO roomDAO;

    public RoomController(boolean isTest){
        roomDAO = RoomDAO.getInstance(isTest);
    }
    public static Handler getRooms() {
        return ctx -> {
            List<Room> roomList = roomDAO.getAll();
            List<RoomDTO> roomDTOList = roomList.stream().map(x -> new RoomDTO(x.getId(), x.getHotelId(), x.getNumber(), x.getPrice())).toList();
            //roomDTOList.forEach(System.out::println);
            ctx.json(roomDTOList);
        };
    }

    public static void getRoomById(Context ctx) {
        int id =Integer.parseInt(ctx.pathParam("id"));
        Room room = roomDAO.getById(id);
        RoomDTO roomDTO = new RoomDTO(room.getId(),room.getHotelId(),room.getNumber(),room.getPrice());
        ctx.json(roomDTO);
    }

    public static void createRoom(Context ctx) {
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        if(roomDTO != null) {
            Room room = new Room(roomDTO.getHotelId(), roomDTO.getNumber(), roomDTO.getPrice());
            Hotel hotel = HotelDAO.getInstance(false).getById(room.getHotelId());
            room.setHotel(hotel);
            roomDAO.create(room);
            ctx.json(room);
        }
    }

    public static void updateRoom(Context ctx) {
        int id =Integer.parseInt(ctx.pathParam("id"));
        ctx.bodyValidator(RoomDTO.class);
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        Room room = roomDAO.getById(id);
        Room updatedRoom = updateRoomFromDTO(room,roomDTO);
        if(updatedRoom != null) {
            roomDAO.update(updatedRoom, room.getId());
            ctx.json(updatedRoom);
        }else {
            ctx.status(400);
            ctx.result("fields do not exits");
        }
    }
    /*
  wanted a way to automatically go through all dto's get methods and set my hotel entity with the values
  if they are not null and different from what is already on the hotel entity.

   this what I ended up with instead:
   */
    private static Room updateRoomFromDTO(Room room,RoomDTO roomDTO){
        // Set of field names to skip during update should be sat as a private static attribute
        // on my update class and when I make a generic iteration of it.
        final Set<String> fieldsToSkip = new HashSet<>(Arrays.asList("id"));
        // gets the class of my dto
        Class<?> dtoClass = roomDTO.getClass();
        // gets all the declared fields in my dto class and stores then in a Field array.
        Field[] dtoFields = dtoClass.getDeclaredFields();
        /*
        I loop through the field array and gets the name of each field.
         */
        for (Field dtoField: dtoFields) {
            if(!dtoField.isSynthetic() && !fieldsToSkip.contains(dtoField.getName())){
                String dtoFieldName = dtoField.getName();
                try{
                    /*
                    finds the hotel field with the same name as the dto field. and throws
                    a no such field exception if none is matching.
                     */
                    Field roomField = room.getClass().getDeclaredField(dtoFieldName);
                    // makes it possible to access and modify the fields.
                    roomField.setAccessible(true);
                    dtoField.setAccessible(true);
                    // if the 2 fields types are equal it gets the values stores on the dto on the field otherwise it skips.
                    // could be replaced with if (roomField.getType().isAssignableFrom(dtoField.getType()))
                    if (dtoField.getType().equals(roomField.getType())) {
                        Object dtoFieldValue = dtoField.get(roomDTO);
                        // if the value gotten is not null it sets the hotels field to the value of the dto field.
                        if (dtoFieldValue != null && !dtoFieldValue.equals(0)) {
                            roomField.set(room, dtoFieldValue);
                        }
                    }
                }catch (Exception    e){
                    return null;
                }
            }
        }
        return room;
    }

    public static void deleteRoom(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Room room = roomDAO.getById(id);
        roomDAO.delete(id);
        ctx.json(room);
    }
}
