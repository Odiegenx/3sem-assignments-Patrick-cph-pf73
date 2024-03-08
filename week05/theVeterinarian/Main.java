package theVeterinarian;

import theVeterinarian.config.ApplicationConfig;
import theVeterinarian.config.Routes;
import theVeterinarian.controllers.AppointmentController;
import theVeterinarian.controllers.PatientController;
import theVeterinarian.model.Appointment;
import theVeterinarian.model.Patient;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Patient patient1 = new Patient(1,"Patrick");
        Patient patient2 = new Patient(2,"Niklas");

        Appointment appointment1 = new Appointment(1,"Teeth check", LocalDate.now(),patient1);
        Appointment appointment2 = new Appointment(2,"Hair trim",LocalDate.now(),patient2);

        AppointmentController.addAppointment(appointment1);
        AppointmentController.addAppointment(appointment2);

        PatientController.addPatient(patient1);
        PatientController.addPatient(patient2);

        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7007)
                .setExceptionOverallHandling()
                .setRoute(Routes.setRoutes());
    }
}
