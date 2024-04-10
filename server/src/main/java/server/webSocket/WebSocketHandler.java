package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
//import dataaccess.DataAccess;
//import exception.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.mySQL.DatabaseAuthDAO;
import dataAccess.mySQL.DatabaseGameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import reqRes.ReqJoinGame;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        //authdata
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session,getUsername(command.getAuthString()), command);
            case JOIN_OBSERVER -> joinObserver(session, getUsername(command.getAuthString()), command);
            case LEAVE -> leaveGame(getUsername(command.getAuthString()));
            case RESIGN -> resignGame(command.getAuthString());
            case MAKE_MOVE -> playerMakeMove(session, getUsername(command.getAuthString()), command);
        }
    }

    private void joinPlayer(Session session, String username, UserGameCommand reqJoinGame) throws IOException, DataAccessException {
        //game data
        try {
            GameData gameData = checkGameID(reqJoinGame.getGameID());
            ChessGame game = gameData.game();
            //ServerMessage.
            connections.add(username, session);
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            var message = String.format("%s has joined as %s player", username, reqJoinGame.getTeamColor());
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION); // set message of notification
            notification.setMessage(message);
            //send message through client
            //session.getRemote.sendText(String json version of msg)
            session.getRemote().sendString(new Gson().toJson(loadGame));
            connections.broadcast(username, notification);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void joinObserver(Session session, String username, UserGameCommand reqJoinGame) throws IOException, DataAccessException {
        try {
            GameData gameData = checkGameID(reqJoinGame.getGameID());
            ChessGame game = gameData.game();
            //ServerMessage.
            connections.add(username, session);
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            var message = String.format("%s has joined as an observer", username);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION); // set message of notification
            notification.setMessage(message);
            //send message through client
            //session.getRemote.sendText(String json version of msg)
            session.getRemote().sendString(new Gson().toJson(loadGame));
            connections.broadcast(username, notification);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private void leaveGame(String username) throws IOException {
        connections.remove(username);
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(username, notification);
    }
    private void resignGame(String username) throws IOException {
        var message = String.format("GAME OVER. %s resigned", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(username, notification);
    }

    public void playerMakeMove(Session session, String username, UserGameCommand command) throws DataAccessException {
        try {
            GameData gameData = checkGameID(command.getGameID());
            ChessGame game = gameData.game();
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            var message = String.format("%s moved", username);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(message);

            session.getRemote().sendString(new Gson().toJson(loadGame));
            connections.broadcast(username, notification);
            connections.broadcast(username, loadGame);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    private GameData checkGameID(int gameID) throws DataAccessException {
        DatabaseGameDAO gameDAO = new DatabaseGameDAO();
        return gameDAO.getGame(gameID);
    }

    private String getUsername(String authtoken) throws DataAccessException {
        DatabaseAuthDAO authDAO = new DatabaseAuthDAO();
        AuthData authObject = authDAO.getAuth(authtoken);
        String username = authObject.username();
        return username;
    }
}
