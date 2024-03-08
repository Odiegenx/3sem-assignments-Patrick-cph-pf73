package excerciseWithJavalinAndCrud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApplicationConfig {
    ObjectMapper om = new ObjectMapper();
    private Javalin app;
    private static ApplicationConfig instance;
    private ApplicationConfig(){
    }
    // now it's a singleton
    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public ApplicationConfig initiateServer(){
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api"; // what ever you want your urls starts with
        });
        app.attribute("bye","Goodbye");

        /*
        Middleware (HTTP Filters):

        Middleware in Javalin allows us to add common functionalities like logging,
        authentication, etc., to our web application.

        */
        app.before( ctx -> {
            HttpServletRequest request = ctx.req();
            System.out.println(request);
        });
        /*
        Here we print out the request before its processed by the route handler.
         */
        app.after(ctx -> {
            HttpServletResponse response = ctx.res();
            System.out.println("----");
            String goodbye = app.attribute("bye");
            System.out.println(goodbye);
            System.out.println(response);
        });
        /*
        similarly to app.before, after logs the response to the console
        after the request has been handled.
         */
        return instance;
    }
    public ApplicationConfig setRoute(EndpointGroup route){
        app.routes(route);
        return instance;
    }

    /*
    Exception Handling:
     */
    public ApplicationConfig setExceptionOverallHandling(){
        app.exception(NumberFormatException.class,(e,ctx) ->{
            ObjectNode node = om.createObjectNode().put("Bad request: Not a number!",e.getMessage());
            ctx.status(400).json(node);
        });
        app.exception(NullPointerException.class,(e,ctx) -> {
            ObjectNode node = om.createObjectNode().put("Bad request: Not found!",e.getMessage());
            ctx.status(404).json(node);
        });
        app.exception(Exception.class, (e,ctx) ->{
            ObjectNode node = om.createObjectNode().put("errorMessage",e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }
    // public ApplicationConfig setException
    public ApplicationConfig startServer(int port){
        app.start(port);
        return instance;
    }
}
