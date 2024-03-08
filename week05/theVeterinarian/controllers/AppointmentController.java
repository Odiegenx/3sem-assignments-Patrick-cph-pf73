package theVeterinarian.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import theVeterinarian.model.Appointment;
import theVeterinarian.model.Patient;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AppointmentController {

    static Map<Integer, Appointment> appointmentMap = new HashMap<>();

    public static void addAppointment(Appointment appointment) {
        appointmentMap.put(appointment.getId(), appointment);
    }

    public static Handler getAppointments() {
        return ctx -> {
                ctx.json(appointmentMap);
                ctx.status(200);
        };
    }
    // Below try catch is redundant, error handling added to Application Config
    public static void getAppointmentById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Appointment toGet = appointmentMap.get(id);
            ctx.json(toGet);
            ctx.status(200);
        } catch (NumberFormatException | NullPointerException e) {
            ctx.status(400);
            ctx.result("Bad request "+ e.getMessage());
        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Error occurred " + e.getMessage());
        }
    }
}
