package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;

import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    public int createGame(ReqCreateGame gameName, String authToken) throws DataAccessException {
        //check auth
        if(authDAO.getAuth(authToken) != null) {
            int gameID = gameDAO.createGame(gameName.gameName());
            return gameID;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken) != null) {
            Collection<GameData> listOfGames = gameDAO.listGames();
            return listOfGames;
        }
        else{
            throw new DataAccessException("Not Authorized");
        }
    }
    public void joinGame(ReqJoinGame joinGameObj, String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken) != null) {
            if(gameDAO.getGame(joinGameObj.gameID()) != null) {
                GameData gameData = gameDAO.getGame(joinGameObj.gameID());
                if(joinGameObj.playerColor() == null) {
                    AuthData authData = authDAO.getAuth(authToken);
                    String username = authData.username();
                    gameDAO.joinGame(joinGameObj, username);
                } else if(joinGameObj.playerColor().equals("WHITE") || joinGameObj.playerColor().equals("BLACK")) {
                    AuthData authData = authDAO.getAuth(authToken);
                    String username = authData.username();
                    gameDAO.joinGame(joinGameObj, username);
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
        gameDAO.clearGames();
    }
}
