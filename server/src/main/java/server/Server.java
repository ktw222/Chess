package server;

import dataAccess.DataAccessException;
import handler.*;
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
    private void createRoutes() {
        Spark.before((req, res) -> System.out.println(
                "Executing route: " + req.pathInfo()));
        Spark.post("/user", new RegisterHandler());
        Spark.delete("/db", new ClearHandler());
        Spark.delete("/session", new LogoutHandler()); //problem
        Spark.post("/session", new LoginHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.get("/get", new ListGamesHandler());
        Spark.exception(Exception.class, this:: exceptionHandler);
        //Spark.post("/session", new LoginHandler());
        //Spark.get("/hello", (req, res) -> "Hello BYU!");
    }
    public void exceptionHandler(Exception ex, Request req, Response res) {
        //res.status(200);
        if(ex.getMessage().equals("Username is taken, pick a new one.")) {
            res.status(403);
            res.body("{\"message\": \"Error: already taken\"}");
        }
        else if (ex.getMessage().equals("Bad Request")) {
            res.status(400);
            res.body("{ \"message\": \"Error: bad request\" }");
        }
        else if (ex.getMessage().equals("Incorrect password") || ex.getMessage().equals("User doesn't exist") ||
                ex.getMessage().equals("Not Authorized")) {
            res.status(401);
            res.body("{ \"message\": \"Error: unauthorized\" }");
        }
        //return ;
    }
    public static void main(String[] args) {new Server().run(8080);}

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
