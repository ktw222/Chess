package service;

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
    private final MemoryAuthDAO memAuthDAO;

    public GameService(MemoryGameDAO memGameDAO, MemoryAuthDAO memAuthDAO) {
        this.memGameDAO = memGameDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public int createGame(ReqCreateGame gameName, String authToken) throws DataAccessException {
        //check auth
        if(memAuthDAO.getAuth(authToken) != null) {
            int gameID = memGameDAO.createGame(gameName.gameName());
            return gameID;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        if(memAuthDAO.getAuth(authToken) != null) {
            Collection<GameData> listOfGames = memGameDAO.listGames();
            return listOfGames;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public void joinGame(ReqJoinGame joinGameObj, String authToken) throws Exception {
        if(memAuthDAO.getAuth(authToken) != null) {
            if(memGameDAO.getGame(joinGameObj.gameID()) != null) {
                AuthData authData = memAuthDAO.getAuth(authToken);
                String username = authData.username();
                memGameDAO.joinGame(joinGameObj, username);
            } else {
                throw new Exception("Bad Request");
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
