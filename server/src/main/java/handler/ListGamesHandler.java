package handler;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;
import reqRes.ReqCreateGame;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Collection;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");

        GameService service = new GameService(memoryGameDAO, memAuthDao);
        Collection<GameData> result = service.listGames(authToken);
        return "{\"games\": " + String.valueOf(result) + "}" ;
    }
}
