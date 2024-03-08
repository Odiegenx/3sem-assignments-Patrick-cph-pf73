package dogExcercise;

import dogExcercise.controllers.DogController;
import dogExcercise.dtos.DogDTO;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    static DogDTO dog1 = new DogDTO(1,"Roling", "chihuahua","Male",3);
    static DogDTO dog2 = new DogDTO(2,"Zoey","Pomeranian", "female",5);
    static DogDTO dog3 = new DogDTO(3,"Hans", "Poodle", "Male",2);
    public static void main(String[] args) {
        DogController.addDog(dog1);
        DogController.addDog(dog2);
        DogController.addDog(dog3);

         Javalin app = Javalin.create().start(7007);
         app.routes(() -> {
            path("/api/dogs", () -> {
                get("/", DogController::getAllDogs);

                /*
                Context Object in Javalin:

                The Javalin Context object provides access to information about the current HTTP request and response.

                Path parameters in Javalin routes allow capturing dynamic parts of URLs
                and passing them as parameters to route handlers.
                 */
                get("/dog/{id}",DogController::getDogResponseByID);
                //This example demonstrates accessing path parameters to fetch user-specific data.

                /*
                its also here ive  setup different routes and mapped them to specific handlers
                 */
                post("/dog", DogController::addDog);
                put("/dog/{id}",DogController::updateDog);
                delete("/dog/{id}",DogController::deleteDog);
            });
         });
    }
}
