package excerciseWithJavalinAndCrud.DAO;

import excerciseWithJavalinAndCrud.model.Hotel;
import jakarta.persistence.EntityManagerFactory;

public class HotelDAO extends DAO<Hotel,Integer>{
    private static HotelDAO instance;

    private HotelDAO(boolean isTest) {
        super(Hotel.class,isTest);
    }
    public static HotelDAO getInstance(boolean isTest){
        if(instance == null){
            instance = new HotelDAO(isTest);
        }
        return instance;
    }
}
