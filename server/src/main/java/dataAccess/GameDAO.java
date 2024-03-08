package dataAccess;

import chess.ChessGame;
import model.GameData;
import reqRes.ReqJoinGame;

import java.util.Collection;

public interface GameDAO {
    public int createGame(String gameName) throws DataAccessException;
    public GameData getGame(int gameID) throws DataAccessException;
    public void joinGame(ReqJoinGame joinGameObj, String username) throws DataAccessException;
    public void clearGames() throws DataAccessException;
    public Collection<GameData> listGames() throws DataAccessException;
}
