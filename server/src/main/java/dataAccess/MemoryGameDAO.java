package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public ArrayList<GameData> listGames() {
        ArrayList<GameData> listOfGames = (ArrayList<GameData>) games.values();
        return listOfGames;
    }
    public void clearGames() {
        games.clear();
    }
}
