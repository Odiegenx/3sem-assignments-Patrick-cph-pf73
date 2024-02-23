package recyclingExercise.dao;

import recyclingExercise.model.Driver;
import recyclingExercise.model.WasteTruck;

import java.util.List;

public interface IWasteTruckDAO {
    // WasteTruck
    int saveWasteTruck(String brand, String registrationNumber, int capacity);
    WasteTruck getWasteTruckById(int id);
    void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available);
    void deleteWasteTruck(int id);
    void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver);
    void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id);
    List<WasteTruck> getAllAvailableTrucks();
}
