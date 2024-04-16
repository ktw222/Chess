package server.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String auth, Session session, Integer gameID) {
        var connection = new Connection(auth, session, gameID);
        connections.put(auth, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String excludeVisitorName, Integer gameID, ServerMessage notification) throws IOException {
        try {
            var closeConnections = new ArrayList<Connection>();

            for (var connection : connections.values()) {
                if (connection.session.isOpen()) {
                    if (!connection.authToken.equals(excludeVisitorName) && connection.gameID.equals(gameID)) {//!connection.visitorName.equals(excludeVisitorName) &&
                        //System.out.println("before connection" + excludeVisitorName + gameID);
                        var sentMsg = new Gson().toJson(notification);
                        connection.send(sentMsg);
                        //System.out.println("after connection" + excludeVisitorName + gameID);
                    }
                } else {
                    closeConnections.add(connection);
                }

            }

            // Clean up any connections that were left open.
            for (var connection : closeConnections) {
                connections.remove(connection.authToken);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}
