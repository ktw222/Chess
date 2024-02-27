package dataAccess;

import model.GameData;
import model.UserData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    final private HashMap<String, GameData> games = new HashMap<>();
    public void clearGames() {
        games.clear();
    }
}
