package client;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint{

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                @OnMessage
                public void onMessage(String message) {
                    //System.out.print("Recieved message: " + message);
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    switch (notification.getServerMessageType()) {
                        case LOAD_GAME -> {
                            try {
                                notificationHandler.loadGame(notification);
                            } catch (ResponseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case NOTIFICATION -> notificationHandler.notify(notification);
                        case ERROR -> notificationHandler.notify(notification);
                    }
                    //notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

        //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
    }



    public void joinPlayer(String authToken, ChessGame.TeamColor playerColor, Integer gameID) throws ResponseException {
        //helperMethod(authToken, playerColor, gameID);
        try {
            var action = new UserGameCommand(authToken, playerColor, gameID);
            action.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void joinObserver(String authToken, ChessGame.TeamColor playerColor, Integer gameID) throws ResponseException {
        //helperMethod(authToken, playerColor, gameID);
        try {
            var action = new UserGameCommand(authToken, playerColor, gameID);
            action.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void makeMove(String authToken, ChessGame.TeamColor playerColor, Integer gameID, ChessMove move) throws ResponseException {
        try {
            var action = new UserGameCommand(authToken, playerColor, gameID);
            action.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
            action.setMove(move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void resign(String authToken, ChessGame.TeamColor playerColor, Integer gameID) throws ResponseException {
        try {
            var action = new UserGameCommand(authToken, playerColor, gameID);
            action.setCommandType(UserGameCommand.CommandType.RESIGN);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leaveGame(String authToken, ChessGame.TeamColor playerColor, Integer gameID) throws ResponseException {
        try {
            var action = new UserGameCommand(authToken, playerColor, gameID);
            action.setCommandType(UserGameCommand.CommandType.LEAVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

}
