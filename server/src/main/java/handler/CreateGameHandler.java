package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import reqRes.ReqCreateGame;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");
        ReqCreateGame request = (ReqCreateGame) gson.fromJson(req.body(), ReqCreateGame.class);
        if(request.gameName() == null) {
            throw new DataAccessException("Bad Request");
        }
        GameService service = new GameService(memoryGameDAO, memAuthDao);
        int result = service.createGame(request, authToken);
        return "{\"gameID\": " + String.valueOf(result) + "}" ;
    }

}
