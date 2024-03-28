package Client;

import model.GameData;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import ui.GameplayUi;
import ui.PostLoginUi;

import java.util.Arrays;
import java.util.HashMap;

public class GameplayClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    private HashMap<Integer, Integer> gameList = new HashMap<>();

    public GameplayClient(ServerFacade server, String serverUrl, GameplayUi gameplayUi) {
        this.server = server;
        this.serverUrl = serverUrl;

    }

    public String eval(String input, String authToken) {
        try {
            this.authToken = authToken;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "possiblemoves" -> highlightMoves(params);
                case "makemove" -> makeMoves(params);
                case "resign" -> resign();
                case "redraw" -> createGame(params);
                case "leaveGame " -> leaveGame();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }

    }
    public String highlightMoves(String... params) throws ResponseException {
        if (params.length >= 2) {
            String pieceRow = params[0];
            String pieceColumn = params[1];
            return String.format("Legal moves for piece: %s %s.", pieceRow, pieceColumn);
        }
        throw new ResponseException(400, "Expected: pieceRow pieceColumn");
    }
    public String makeMoves(String... params) throws ResponseException {
        if (params.length >= 4) {
            String origPieceRow = params[0];
            String origPieceColumn = params[1];
            String newPieceRow = params[2];
            String newPieceColumn = params[3];
            String promotionPiece = null;
            if(params.length > 4) {
                promotionPiece = params[4];
            }
            return String.format("You moved %s%s to %s%s. Promotion: %s", origPieceRow, origPieceColumn, newPieceRow, newPieceColumn, promotionPiece);
        }
        throw new ResponseException(400, "Expected: currPieceRow currPieceCol moveRow moveCol promotionChoice");
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
        GameData[] games = server.listGames(authToken);
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

    public String leaveGame() throws ResponseException {
        //server.logout(authToken);
        return String.format("Leaving game");
    }
    public String resign() throws ResponseException {
        //server.logout(authToken);
        return String.format("You resigned");
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
