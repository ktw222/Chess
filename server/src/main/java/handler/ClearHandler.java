package handler;
import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.mySQL.DatabaseAuthDAO;
import dataAccess.mySQL.DatabaseUserDAO;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;

public class ClearHandler implements Route{
    @Override
    public Object handle(Request req, Response res) throws Exception {
        AuthDAO authDao = new DatabaseAuthDAO();
        UserDAO userDAO = new DatabaseUserDAO();
        MemoryGameDAO memGameDAO = new MemoryGameDAO();
        Gson gson = new Gson();
        //UserData request = (UserData)gson.fromJson(req.body(), UserData.class);

        UserService userService = new UserService(userDAO, authDao);
        userService.clearUsers();
        GameService gameService = new GameService(memGameDAO, authDao);
        gameService.clearGames();
        AuthService authService = new AuthService(userDAO, authDao);
        authService.clearAuths();
        //ClearResult result;
        return "{}";
    }

}
