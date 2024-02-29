package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinHandler implements Route {

    @Override
    public Object handle(Request req, Response res) throws DataAccessException {
        MemoryAuthDAO memAuthDao = new MemoryAuthDAO();
        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");
        System.out.println(req.body());
        ReqJoinGame request = (ReqJoinGame) gson.fromJson(req.body(), ReqJoinGame.class);
        System.out.println(request);
        if(request.gameID() == null) {
            throw new DataAccessException("Bad Request");
        }
        GameService service = new GameService(memoryGameDAO, memAuthDao);
        service.joinGame(request, authToken);
        return "{}";
    }
}
