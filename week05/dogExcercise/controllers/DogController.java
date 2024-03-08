package dogExcercise.controllers;

import dogExcercise.dtos.DogDTO;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;


public class DogController {
    static Map<Integer, DogDTO> dogMap = new HashMap<>();

    public static void addDog(DogDTO dog){
        dogMap.put(dog.getId(),dog);
    }
   public static void getAllDogs(Context ctx){
        ctx.json(dogMap);
    }
    public static void getDogResponseByID(Context ctx){
        // Here's how we can access request parameters using the context object:
        int id = Integer.parseInt(ctx.pathParam("id"));
        // seems like javelin automatically returns thr 404 status when nothing is found. But now we just make sure
        if(dogMap.containsKey(id)) {
            // were we get the dog information
            ctx.json(dogMap.get(id));
        }else{
            ctx.status(404);
            ctx.result("404 Not Found - Resource not found");
        }
    }
    public static void deleteDog(Context ctx) {
        int id = Integer.valueOf(ctx.pathParam("id"));
        DogDTO deletedDog = dogMap.get(id);
        dogMap.remove(id);
        ctx.json(deletedDog);
    }
    public static void updateDog(Context ctx) {
        int id = Integer.valueOf(ctx.pathParam("id"));
        DogDTO incomingDog = ctx.bodyAsClass(DogDTO.class);
        incomingDog.setId(id);
        dogMap.put(id,incomingDog);
        ctx.json(dogMap.get(id));
    }
    public static void addDog(Context ctx){
        DogDTO incomingDog = ctx.bodyAsClass(DogDTO.class);
        incomingDog.setId(dogMap.size()+1);
        dogMap.put(incomingDog.getId(), incomingDog);
        ctx.json(incomingDog);
    }
}
