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

import java.io.*;
import java.net.*;

public class LoginHandler implements Route {
    //decode
    @Override
    public Object handle(Request req, Response res) throws Exception {
        AuthDAO authDao = new DatabaseAuthDAO();
        UserDAO userDAO = new DatabaseUserDAO();
        Gson gson = new Gson();

        UserData request = (UserData) gson.fromJson(req.body(), UserData.class);
        if(request.username() == null || request.password() ==null ) {
            throw new DataAccessException("Bad Request");
        }
        UserService service = new UserService(userDAO, authDao);
        AuthData result = service.login(request);
        return gson.toJson(result);
    }

}
