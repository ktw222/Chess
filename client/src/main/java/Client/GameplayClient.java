package Client;

import model.GameData;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import ui.GameplayUi;
import ui.PostLoginUi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
                case "redraw" -> redrawBoard();
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

    public String redrawBoard() throws ResponseException {
        return String.format("Board successfully redrawn!");
    }



    public String leaveGame() throws ResponseException {
        //server.logout(authToken);
        return String.format("Leaving game");
    }
    public String resign() throws ResponseException {
        String.format("Resigning will cause you to forfeit the game. Are you sure you wish to resign?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        switch (cmd) {
            case "yes" -> {
                return String.format("You resigned");
            }
            case "no" -> {
                return String.format("You successfully canceled your resignation");
            }
        }

        //server.logout(authToken);
        throw new ResponseException(400, "Expected: <YES> <NO>");
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
