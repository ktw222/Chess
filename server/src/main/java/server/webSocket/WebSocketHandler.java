package server.webSocket;

import chess.ChessGame;
import chess.ChessMove;
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
import java.util.ArrayList;


@WebSocket
public class WebSocketHandler {
    //how to broadcast just to my game
    //resign
    //valid moves array contains nothing
    //keep track of players and observers in specific game //in connection manager
    private final ConnectionManager connections = new ConnectionManager();
    private ChessGame game;

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        //authdata
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        switch (command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session,getUsername(session, command.getAuthString()), command);
            case JOIN_OBSERVER -> joinObserver(session, getUsername(session, command.getAuthString()), command);
            case LEAVE -> leaveGame(getUsername(session, command.getAuthString()), command);
            case RESIGN -> resignGame(session, getUsername(session, command.getAuthString()), command);
            case MAKE_MOVE -> playerMakeMove(session, getUsername(session, command.getAuthString()), command);
        }
    }

    private void joinPlayer(Session session, String username, UserGameCommand reqJoinGame) throws IOException, DataAccessException {
        //game data
        try {
            GameData gameData = checkGameID(session, reqJoinGame.getGameID());
            ChessGame game = gameData.game();
            if(gameData.whiteUsername() == null || gameData.blackUsername() == null) {
                var errorMessage = String.format("Error: game is empty");
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
                throw new DataAccessException("Error: Black username not black");
            }
            if(reqJoinGame.getPlayerColor() != null) {
                if (reqJoinGame.getPlayerColor().equals(ChessGame.TeamColor.BLACK)) {
                    if (!gameData.blackUsername().equals(username)) {
                        var errorMessage = String.format("Error: Black username not black");
                        var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                        error.setErrorMessage(errorMessage);
                        session.getRemote().sendString(new Gson().toJson(error));
                        throw new DataAccessException("Error: Black username not black");
                    }
                } else if (reqJoinGame.getPlayerColor().equals(ChessGame.TeamColor.WHITE)) {
                    if (!gameData.whiteUsername().equals(username)) {
                        var errorMessage = String.format("Error: White username not white");
                        var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                        error.setErrorMessage(errorMessage);
                        session.getRemote().sendString(new Gson().toJson(error));
                        throw new DataAccessException("Error: White username not white");
                    }
                }
            }
            connections.add(username, session, reqJoinGame.getGameID());
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            var message = String.format("%s has joined as %s player", username, reqJoinGame.getPlayerColor());
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION); // set message of notification
            notification.setMessage(message);
            //send message through client
            //session.getRemote.sendText(String json version of msg)
            session.getRemote().sendString(new Gson().toJson(loadGame));
            connections.broadcast(username, reqJoinGame.getGameID(), notification);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void joinObserver(Session session, String username, UserGameCommand reqJoinGame) throws IOException, DataAccessException {
        try {
            GameData gameData = checkGameID(session, reqJoinGame.getGameID());
            ChessGame game = gameData.game();
            //ServerMessage.
            connections.add(username, session, reqJoinGame.getGameID());
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            var message = String.format("%s has joined as an observer", username);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION); // set message of notification
            notification.setMessage(message);
            //send message through client
            //session.getRemote.sendText(String json version of msg)
            session.getRemote().sendString(new Gson().toJson(loadGame));
            connections.broadcast(username,reqJoinGame.getGameID(), notification);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private void leaveGame(String username, UserGameCommand command) throws IOException {
        connections.remove(username);
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(username, command.getGameID(), notification);
    }
    private void resignGame(Session session, String username, UserGameCommand command) throws IOException, DataAccessException {
        GameData gameData = checkGameID(session, command.getGameID());
        ChessGame game = gameData.game();
        if (!username.equals(gameData.whiteUsername()) && !username.equals(gameData.blackUsername())) {
            var errorMessage = String.format("Error: cannot resign as observer");
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(errorMessage);
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if(game.getTeamTurn() == null) {
            var errorMessage = String.format("Error: Game Over. No further actions can be taken");
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(errorMessage);
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        var message = String.format("GAME OVER. %s resigned", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(username, command.getGameID(), notification);
        game.setTeamTurn(null);
    }

    public void playerMakeMove(Session session, String username, UserGameCommand command) throws DataAccessException {
        try {
            GameData gameData = checkGameID(session, command.getGameID());
            ChessGame game = gameData.game();
            ChessGame.TeamColor teamTurn = game.getTeamTurn();
            if(game.isInCheckmate(teamTurn)) {
                teamTurn = null;
            }
            if(teamTurn == null) {
                var errorMessage = String.format("Error: Game Over no moves can be made");
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
                return;
            }
            ChessGame.TeamColor playerColor = null;
            if(username.equals(gameData.blackUsername())) {
                playerColor = ChessGame.TeamColor.BLACK;
            } else if(username.equals(gameData.whiteUsername())) {
                playerColor = ChessGame.TeamColor.WHITE;
            } else {
                var errorMessage = String.format("Error: cannot make move as observer");
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
                return;
            }
            //board
            ChessMove move = command.getChessMove();
            var validMoves = game.validMoves(move.getStartPosition());
            if(!game.validMoves(move.getStartPosition()).contains(move)){
                var errorMessage = String.format("Error: Invalid move");
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
                return;
            }
            //ChessGame.TeamColor playerColor = command.getPlayerColor();
            if(game.getTeamTurn().equals(playerColor)) {
                var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                loadGame.setGame(game);
                var message = String.format("%s moved", username);
                var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                notification.setMessage(message);

                session.getRemote().sendString(new Gson().toJson(loadGame));
                connections.broadcast(username, command.getGameID(), notification);
                connections.broadcast(username, command.getGameID(), loadGame);
            } else {
                var errorMessage = String.format("Error: cannot make move on other players turn");
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
            }
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    private GameData checkGameID(Session session, int gameID) throws DataAccessException, IOException {
        try {
            DatabaseGameDAO gameDAO = new DatabaseGameDAO();
            GameData gameData = gameDAO.getGame(gameID);
            if (gameData == null) {
                var errorMessage = String.format("Error: Game not found for gameID %d", gameID);
                var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage(errorMessage);
                session.getRemote().sendString(new Gson().toJson(error));
                throw new DataAccessException("Game not found");
            }
            return gameData;
        } catch (Exception ex) {
            throw new DataAccessException("Game not found");
        }
    }

    private String getUsername(Session session, String authtoken) throws DataAccessException, IOException {
        try {
            DatabaseAuthDAO authDAO = new DatabaseAuthDAO();
            AuthData authObject = authDAO.getAuth(authtoken);
            String username = authObject.username();
            return username;
        } catch (Exception ex) {
            var errorMessage = String.format("Error: unauthorized access");
            var error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(errorMessage);
            session.getRemote().sendString(new Gson().toJson(error));
            throw new DataAccessException("unauthorized access");
        }
    }
}
