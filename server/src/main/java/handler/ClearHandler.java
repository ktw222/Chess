package handler;
import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;

public class ClearHandler implements Route{
    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
        MemoryGameDAO memGameDAO = new MemoryGameDAO();
        Gson gson = new Gson();
        //UserData request = (UserData)gson.fromJson(req.body(), UserData.class);

        UserService userService = new UserService(memoryUserDAO, memAuthDao);
        userService.clearUsers();
        GameService gameService = new GameService(memGameDAO, memAuthDao);
        gameService.clearGames();
        AuthService authService = new AuthService(memoryUserDAO, memAuthDao);
        authService.clearAuths();
        //ClearResult result;
        return "{}";
    }

}
