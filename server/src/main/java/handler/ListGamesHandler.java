package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.mySQL.DatabaseAuthDAO;
import dataAccess.mySQL.DatabaseGameDAO;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request req, Response res) throws Exception {
        AuthDAO authDao = new DatabaseAuthDAO();
        GameDAO gameDAO = new DatabaseGameDAO();
        Gson gson = new Gson();
        String authToken = req.headers("Authorization");

        GameService service = new GameService(gameDAO, authDao);
        Collection<GameData> result = service.listGames(authToken);
        System.out.println(result);
        StringBuilder outputString;
        outputString = new StringBuilder("{ \"games\": [");
        boolean isFirst = true;

        for(GameData data : result) {

            if(isFirst != true){
                outputString.append(", ");
            }
            outputString.append("{\"gameID\": ").append(String.valueOf(data.gameID()));
            outputString.append(", \"whiteUsername\":");
            if(data.whiteUsername() == null) {
            }else if(data.whiteUsername() != null) {
                outputString.append("\"");
            }
            outputString.append(data.whiteUsername());
            if(data.whiteUsername() == null) {
            }if(data.whiteUsername() != null) {
                outputString.append("\"");
            }
            outputString.append(", \"blackUsername\":");
            if(data.blackUsername() == null) {
            }if(data.blackUsername() != null) {
                outputString.append("\"");
            }
            outputString.append(data.blackUsername());
            if(data.blackUsername() == null) {
            } else if(data.blackUsername() != null) {
                outputString.append("\"");
            }
            outputString.append(", \"gameName\":\"").append(data.gameName());
            outputString.append("\"}");
            isFirst = false;
        }
        outputString.append("]}");
        System.out.println(outputString.toString());

        return outputString.toString();
    }
}
