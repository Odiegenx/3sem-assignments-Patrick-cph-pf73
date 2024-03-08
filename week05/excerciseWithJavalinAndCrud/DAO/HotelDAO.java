package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.model.Hotel;
import jakarta.persistence.EntityManagerFactory;

public class HotelDAO extends DAO<Hotel,Integer>{
    private static HotelDAO instance;

    private HotelDAO() {
        super(Hotel.class);
    }
    public static HotelDAO getInstance(){
        if(instance == null){
            instance = new HotelDAO();
        }
        return instance;
    }
}
