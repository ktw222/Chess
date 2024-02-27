package server;

import handler.ClearHandler;
import handler.LoginHandler;
import handler.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }
    //create routes
    private static void createRoutes() {
        Spark.before((req, res) -> System.out.println(
                "Executing route: " + req.pathInfo()));
        Spark.post("/user", new RegisterHandler());
        Spark.delete("/db", new ClearHandler());
        //Spark.post("/session", new LoginHandler());
        //Spark.get("/hello", (req, res) -> "Hello BYU!");
    }
    public static void main(String[] args) {new Server().run(8080);}

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
