package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.model.Room;

public class RoomDAO extends DAO<Room,Integer> {
    private static RoomDAO instance;

    private RoomDAO() {
        super(Room.class);
    }
    public static RoomDAO getInstance(){
        if(instance == null){
            instance = new RoomDAO();
        }
        return instance;
    }
}
