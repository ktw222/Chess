package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;

import java.util.ArrayList;
import java.util.Collection;

public class GameService {
    private final MemoryGameDAO memGameDAO;
    private final AuthDAO authDAO;

    public GameService(MemoryGameDAO memGameDAO, AuthDAO authDAO) {
        this.memGameDAO = memGameDAO;
        this.authDAO = authDAO;
    }
    public int createGame(ReqCreateGame gameName, String authToken) throws DataAccessException {
        //check auth
        if(authDAO.getAuth(authToken) != null) {
            int gameID = memGameDAO.createGame(gameName.gameName());
            return gameID;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken) != null) {
            Collection<GameData> listOfGames = memGameDAO.listGames();
            return listOfGames;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public void joinGame(ReqJoinGame joinGameObj, String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken) != null) {
            if(memGameDAO.getGame(joinGameObj.gameID()) != null) {
                GameData gameData = memGameDAO.getGame(joinGameObj.gameID());
                if(joinGameObj.playerColor() == null) {
                    AuthData authData = authDAO.getAuth(authToken);
                    String username = authData.username();
                    memGameDAO.joinGame(joinGameObj, username);
                } else if(joinGameObj.playerColor().equals("WHITE") || joinGameObj.playerColor().equals("BLACK")) {
                    AuthData authData = authDAO.getAuth(authToken);
                    String username = authData.username();
                    memGameDAO.joinGame(joinGameObj, username);
                } else {
                    throw new DataAccessException("Bad Request");
                }
            } else {
                throw new DataAccessException("Bad Request");
            }
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public void clearGames() throws DataAccessException {
        memGameDAO.clearGames();
    }
}
