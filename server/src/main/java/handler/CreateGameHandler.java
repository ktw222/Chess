package handler;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.mySQL.DatabaseAuthDAO;
import dataAccess.mySQL.DatabaseGameDAO;
import reqRes.ReqCreateGame;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        AuthDAO authDao = new DatabaseAuthDAO();
        GameDAO gameDAO = new DatabaseGameDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");
        ReqCreateGame request = (ReqCreateGame) gson.fromJson(req.body(), ReqCreateGame.class);
        if(request.gameName() == null) {
            throw new DataAccessException("Bad Request");
        }
        GameService service = new GameService(gameDAO, authDao);
        int result = service.createGame(request, authToken);
        return "{\"gameID\": " + String.valueOf(result) + "}" ;
    }

}
