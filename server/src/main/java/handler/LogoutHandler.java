package handler;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.mySQL.DatabaseAuthDAO;
import dataAccess.mySQL.DatabaseUserDAO;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        AuthDAO authDao = new DatabaseAuthDAO();
        UserDAO userDAO = new DatabaseUserDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");
        if(authToken == null ) {
            throw new DataAccessException("Bad Request");
        }
        UserService service = new UserService(userDAO, authDao);
        service.logout(authToken);
        return "{}";
    }
}
