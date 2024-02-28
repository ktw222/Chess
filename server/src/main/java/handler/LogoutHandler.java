package handler;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
        Gson gson = new Gson();

        AuthData request = (AuthData) gson.fromJson(req.body(), AuthData.class);
        if(request.authToken() == null ) {
            throw new Exception("Bad Request");
        }
        UserService service = new UserService(memoryUserDAO, memAuthDao);
        service.logout(request);
        return "{}";
    }
}
