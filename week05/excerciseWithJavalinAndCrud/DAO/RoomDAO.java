package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.model.Room;

public class RoomDAO extends DAO<Room,Integer> {
    private static RoomDAO instance;
    private RoomDAO(boolean isTest) {
        super(Room.class,isTest);
    }

    public static RoomDAO getInstance(boolean isTest){
        if(instance == null){
            instance = new RoomDAO(isTest);
        }
        return instance;
    }
}
