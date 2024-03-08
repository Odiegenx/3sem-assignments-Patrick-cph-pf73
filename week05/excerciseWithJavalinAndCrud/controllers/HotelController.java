package excerciseWithJavalinAndCrud.controllers;

import excerciseWithJavalinAndCrud.DAO.HotelDAO;
import excerciseWithJavalinAndCrud.DAO.RoomDAO;
import excerciseWithJavalinAndCrud.DTO.HotelDTO;
import excerciseWithJavalinAndCrud.DTO.RoomDTO;
import excerciseWithJavalinAndCrud.model.Hotel;
import excerciseWithJavalinAndCrud.model.Room;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelController {

    private static HotelDAO hotelDAO;

    public HotelController(){
        hotelDAO = HotelDAO.getInstance();
    }


    /*
    Route Handlers:

    Route handlers are functions that handle specific
    HTTP endpoints defined in our application.
     */
    public static Handler getHotels() {
        return ctx -> {
          List<Hotel> hotelList = hotelDAO.getAll();
          List<HotelDTO> hotelDTOList = hotelList.stream().map(x -> new HotelDTO(x.getId(),x.getName(),x.getAddress(),x.getRooms())).toList();
          hotelDTOList.forEach(System.out::println);
          ctx.json(hotelDTOList);
        };
    }

    public static void getHotelById(Context ctx) {
        int id =Integer.parseInt(ctx.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        HotelDTO hotelDTO = new HotelDTO(hotel.getId(), hotel.getName(), hotel.getAddress(), hotel.getRooms());
        ctx.json(hotelDTO);
    }

    public static void getHotelRoomsByHotelId(Context ctx) {
        int id =Integer.parseInt(ctx.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        List<RoomDTO> rooms = hotel.getRooms().stream().map(x -> new RoomDTO(x.getId(),x.getHotelId(),x.getNumber(),x.getPrice())).toList();
        ctx.json(rooms);
    }

    public static void createHotel(Context ctx) {
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        Hotel hotel = new Hotel(hotelDTO.getName(), hotelDTO.getAddress());
        if(hotelDTO.getRooms() != null) {
            hotelDTO.getRooms().stream().forEach(x -> hotel.addRoom(x));
        }
        hotelDAO.create(hotel);
        ctx.json(hotel);
    }

    public static void updateHotel(Context ctx) {
        int id =Integer.parseInt(ctx.pathParam("id"));
        ctx.bodyValidator(HotelDTO.class);
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        Hotel hotel = hotelDAO.getById(id);
        Hotel updatedHotel = updateHotelFromDTO(hotel,hotelDTO);
        if(updatedHotel != null) {
            hotelDAO.update(updatedHotel, hotel.getId());
            ctx.json(updatedHotel);
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
    private static Hotel updateHotelFromDTO(Hotel hotel,HotelDTO hotelDTO){
        // Set of field names to skip during update should be sat as a private static attribute
        // on my update class and when I make a generic iteration of it.
        final Set<String> fieldsToSkip = new HashSet<>(Arrays.asList("id"));
        // gets the class of my dto
        Class<?> dtoClass = hotelDTO.getClass();
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
                    Field hotelField = hotel.getClass().getDeclaredField(dtoFieldName);
                    // makes it possible to access and modify the fields.
                    hotelField.setAccessible(true);
                    dtoField.setAccessible(true);
                    // if the 2 fields types are equal it gets the values stores on the dto on the field otherwise it skips.
                    // could be replaced with if (hotelField.getType().isAssignableFrom(dtoField.getType()))
                    if (dtoField.getType().equals(hotelField.getType())) {
                        Object dtoFieldValue = dtoField.get(hotelDTO);
                        // if the value gotten is not null it sets the hotels field to the value of the dto field.
                        if (dtoFieldValue != null) {
                            hotelField.set(hotel, dtoFieldValue);
                        }
                    }
                }catch (Exception    e){
                    return null;
                }
            }
        }
        return hotel;
    }

    public static void deleteHotel(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        hotelDAO.delete(id);
        ctx.json(hotel);
    }
}
