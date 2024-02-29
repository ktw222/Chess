package dataAccess;

import chess.ChessGame;
import model.GameData;
import reqRes.ReqJoinGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private static int nextID = 1;
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
   public void joinGame(ReqJoinGame joinGameObj, String username) throws DataAccessException {
        System.out.println(joinGameObj);
        GameData currGame = games.get(joinGameObj.gameID());
        if(joinGameObj.playerColor() == null){
            return;
        }
        else if(joinGameObj.playerColor().equals("BLACK")) {
            if (currGame.blackUsername() == null) {
                GameData newData = currGame.setBlackUser(username);
                games.put(currGame.gameID(), newData);
                System.out.println(newData);
            } else {
                throw new DataAccessException("Color already taken");
            }
        }
        else if (joinGameObj.playerColor().equals("WHITE")) {
            if (currGame.whiteUsername() == null) {
                GameData newData = currGame.setWhiteUser(username);
                games.put(currGame.gameID(), newData);
            } else {
                throw new DataAccessException("Color already taken");
            }
        }
    }

    public void clearGames() {
        games.clear();
    }
}