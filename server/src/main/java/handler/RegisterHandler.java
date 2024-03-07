package handler;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import dataAccess.mySQL.DatabaseUserDAO;
import model.AuthData;
import model.UserData;
import service.UserService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    //take in register request as json


    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        UserDAO userDAO = new DatabaseUserDAO();
        Gson gson = new Gson();

        UserData request = (UserData) gson.fromJson(req.body(), UserData.class);
        if(request.username() == null || request.password() == null || request.email() == null) {
            throw new DataAccessException("Bad Request");
        }
        UserService service = new UserService(userDAO, memAuthDao);
        AuthData result = service.register(request);
        return gson.toJson(result);
    }


    //make it a json object
    //send to service
    //repackage to json
    //send it back to http
}
