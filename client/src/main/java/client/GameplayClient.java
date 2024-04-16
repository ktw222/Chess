package client;

import chess.ChessGame;
import ui.GameplayUi;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameplayClient {
    private String visitorName = null;
    //private final ServerFacade server;
    //private final String serverUrl;
    private String authToken;
    ChessGame.TeamColor playerColor;
    private Integer gameID;
    private HashMap<Integer, Integer> gameList = new HashMap<>();
    private WebSocketFacade ws;
    private PrintStream out;
    public GameplayClient(String authToken, Integer gameID, ChessGame.TeamColor playerColor, WebSocketFacade ws,
                          PrintStream out) throws ResponseException {
        this.authToken = authToken;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.ws = ws;
        this.out = out;
    }

    public String eval(String input) {
        try {
            this.authToken = authToken;
            this.gameID = gameID;
            this.playerColor = playerColor;
            //this.ws = ws;
            this.out = out;
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "possiblemoves" -> highlightMoves(params);
                case "makemove" -> makeMoves(params);
                case "resign" -> resign();
                case "redraw" -> redrawBoard();
                case "leavegame" -> leaveGame();
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
            return String.format("Legal moves for piece: %s %s\n", pieceRow, pieceColumn);
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
            //ws.makeMove(authToken, playerColor, gameID);
            return String.format("You moved %s %s to %s %s position. Promotion: %s", origPieceRow, origPieceColumn, newPieceRow, newPieceColumn, promotionPiece);
        }
        throw new ResponseException(400, "Expected: currPieceRow currPieceCol moveRow moveCol promotionChoice");
    }

    public String redrawBoard() throws ResponseException {
        return String.format("Board successfully redrawn!\n");
    }



    public String leaveGame() throws ResponseException {
        //server.logout(authToken);
        ws.leaveGame(authToken, playerColor, gameID);
        return String.format("Left game\n");
    }
    public String resign() throws ResponseException {
        out.println("Resigning will cause you to forfeit the game. Are you sure you wish to resign?");
        //String.format("Resigning will cause you to forfeit the game. Are you sure you wish to resign?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        switch (cmd) {
            case "yes" -> {
                String output = String.format("You resigned");
                ws.resign(authToken, playerColor, gameID);
                return output;
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
                    - PossibleMoves <row col>
                    - MakeMove <originalRow originalCol moveRow moveCol>
                    - Resign
                    - Redraw
                    - leaveGame
                    - Quit
                    """;

    }

}
