package dataAccess;

import chess.ChessGame;
import model.GameData;
import reqRes.ReqJoinGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private int nextID = 1;
    final static private HashMap<Integer, GameData> games = new HashMap<>();
    public int createGame(String gameName) {
        int currID = nextID++;
        GameData game = new GameData(currID, null, null, gameName, new ChessGame());
        games.put(game.gameID(), game);
        return game.gameID();
    }
    public Collection<GameData> listGames() {Collection<GameData> listOfGames = games.values();
        return listOfGames;
    }
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }
   public void joinGame(ReqJoinGame joinGameObj, String username) throws Exception {
        GameData currGame = games.get(joinGameObj.gameID());
        if(joinGameObj.ClientColor().equals("BLACK")) {
            if (currGame.blackUsername() == null) {
                GameData newData = currGame.setBlackUser(username);
                games.put(currGame.gameID(), newData);
            } else {
                throw new Exception("Color already taken");
            }
        }
        if (joinGameObj.ClientColor().equals("WHITE")) {
            if (currGame.whiteUsername() == null) {
                GameData newData = currGame.setWhiteUser(username);
                games.put(currGame.gameID(), newData);
            } else {
                throw new Exception("Color already taken");
            }
        }
    }

    public void clearGames() {
        games.clear();
    }
}