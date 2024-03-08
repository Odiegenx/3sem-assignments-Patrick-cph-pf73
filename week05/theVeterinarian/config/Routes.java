package theVeterinarian.config;

import io.javalin.apibuilder.EndpointGroup;
import theVeterinarian.controllers.AppointmentController;
import theVeterinarian.controllers.PatientController;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    public static EndpointGroup setRoutes(){
        return () -> {
          path("/",getPatientRoutes());
          //path("/",getAppointmentRoutes());
        };
    }
    private static EndpointGroup getPatientRoutes(){
        return  () ->{
            path("/patients", () -> {
                get("/", PatientController.getPatients());
                get("/patient/{id}",PatientController::getPatientById);
            });
            path("/appointments",() -> {
                get("/", AppointmentController.getAppointments());
                get("/appointment/{id}",AppointmentController::getAppointmentById);
            });
        };
    }
   /* private static EndpointGroup getAppointmentRoutes(){
       return  () -> {
            path("/appointments",() -> {
                get("/", AppointmentController.getAppointments());
                get("/appointment/{id}",AppointmentController::getAppointmentById);
            });
        };
    }*/
}
