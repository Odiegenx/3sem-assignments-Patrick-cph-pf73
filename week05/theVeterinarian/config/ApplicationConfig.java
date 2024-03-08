package theVeterinarian.config;

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
            config.routing.contextPath = "/api/vet"; // what ever you want your urls starts with
        });
        app.attribute("bye","Goodbye");
        app.before( ctx -> {
            HttpServletRequest request = ctx.req();
            System.out.println(request);
        });
        app.after(ctx -> {
            HttpServletResponse response = ctx.res();
            System.out.println("----");
            String goodbye = app.attribute("bye");
            System.out.println(goodbye);
            System.out.println(response);
        });
        return instance;
    }
    public ApplicationConfig setRoute(EndpointGroup route){
        app.routes(route);
        return instance;
    }
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
