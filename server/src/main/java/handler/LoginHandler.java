package handler;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import dataAccess.mySQL.DatabaseUserDAO;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.*;
import java.net.*;

public class LoginHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        UserDAO userDAO = new DatabaseUserDAO();
        Gson gson = new Gson();

        UserData request = (UserData) gson.fromJson(req.body(), UserData.class);
        if(request.username() == null || request.password() ==null ) {
            throw new DataAccessException("Bad Request");
        }
        UserService service = new UserService(userDAO, memAuthDao);
        AuthData result = service.login(request);
        return gson.toJson(result);
    }

}
