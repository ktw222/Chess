package client;

import webSocketMessages.serverMessages.ServerMessage;
public interface NotificationHandler {
    void loadGame(ServerMessage notification) throws ResponseException;
    void error(ServerMessage notification);
    void notify(ServerMessage notification);
}
