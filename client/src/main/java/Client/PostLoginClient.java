package Client;

import chess.ChessGame;
import model.GameData;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import ui.PostLoginUi;

import java.util.Arrays;
import java.util.HashMap;

public class PostLoginClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    public Integer gameID;
    private HashMap<Integer, Integer> gameList = new HashMap<>();
    private final NotificationHandler notificationHandler;
    public WebSocketFacade ws;

    public PostLoginClient(ServerFacade server, String serverUrl, PostLoginUi postLoginUi, NotificationHandler notificationHandler) {
        this.server = server;
        this.serverUrl = serverUrl;

        this.notificationHandler = notificationHandler;
    }

    public String eval(String input, String authToken) {
        try {
            this.authToken = authToken;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "listgames" -> listGames();
                case "logout" -> logOut();
                case "joingame" -> joinGame(params);
                case "creategame" -> createGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }

    }//set ws instance in gameplay
    public String joinGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            int gameID = Integer.parseInt(params[0]);
            this.gameID = gameID;
            String playerColor = null;
            if(params.length > 1) {
                playerColor = params[1];
                playerColor = playerColor.toUpperCase();
            }
            ws = new WebSocketFacade(serverUrl, notificationHandler);
            server.joinGame(new ReqJoinGame(gameList.get(gameID), playerColor), authToken);
            if(playerColor!= null) {
                if(playerColor.equals("WHITE")) {
                    ws.joinPlayer(authToken, ChessGame.TeamColor.WHITE, gameID);
                    return String.format("You successfully joined your game as white player!\n");
                } else if(playerColor.equals("BLACK")) {
                    ws.joinPlayer(authToken, ChessGame.TeamColor.BLACK, gameID);
                    return String.format("You successfully joined your game as black player!\n");
                }
            } else {
                ws.joinObserver(authToken, null, gameID);
                return String.format("You successfully joined your game as observer!\n");
            }

        }
        throw new ResponseException(400, "Expected: <gameID playerColor>");
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            String gameName = params[0];
            int gameID = server.createGame(authToken, new ReqCreateGame(gameName));
            return String.format("You created %s. Assigned ID: %d", gameName, gameID);

        }
        throw new ResponseException(400, "Expected: <gameName>");
    }

    public String listGames() throws ResponseException {
        GameData [] games = server.listGames(authToken);
        StringBuilder result = new StringBuilder();
        int counter = 1;
        //HashMap<Integer, Integer> mappedIDs= new HashMap<Integer, Integer>();
        for(GameData game : games) {
            result.append(counter + ". " + game.gameName() + ": Players: ");
            if (game.whiteUsername() != null) {
                result.append("White: " + game.whiteUsername()+ " ");
            }else {
                result.append("White: ___ ");
            }
            if (game.blackUsername() != null) {
                result.append("Black: " + game.blackUsername());
            } else {
                result.append("Black: ___");
            }
            result.append("\n");
            gameList.put(counter, game.gameID());
            counter++;
        }
        return result.toString();

    }

    public String logOut() throws ResponseException {
        server.logout(authToken);
        return String.format("Logout successful");
    }



    public String help() {
            return """
                    - CreateGame <gameName>
                    - JoinGame <gameID>
                    - ListGames <>
                    - Logout
                    - Quit
                    """;

    }

}
