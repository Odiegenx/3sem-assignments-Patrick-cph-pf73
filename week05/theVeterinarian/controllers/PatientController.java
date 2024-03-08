package theVeterinarian.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import theVeterinarian.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientController {

    static Map<Integer, Patient> patientMap = new HashMap<>();

    public static void addPatient(Patient patient){
        patientMap.put(patient.getId(),patient);
    }

    public static Handler getPatients() {
        return ctx -> {
            ctx.json(patientMap);
            ctx.status(200);
        };
    }
    public static void getPatientById(Context ctx) {
        int id = Integer.valueOf(ctx.pathParam("id"));
        Patient toGet = patientMap.get(id);
        ctx.json(toGet);
    }
}
