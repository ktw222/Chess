package ui;
import client.*;
import chess.*;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;


import static java.lang.System.out;
import static ui.EscapeSequences.*;
public class GameplayUi implements NotificationHandler{
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String PAWN = " P ";
    private static final String KING = " K ";
    private static final String QUEEN = " Q ";
    private static final String KNIGHT = " N ";
    private static final String BISHOP = " B ";
    private static final String ROOK = " R ";
    private String authToken;
    private ServerFacade server;
    private ChessGame.TeamColor joinType;
    private int gameId;
    private String serverUrl;
    private GameplayClient client;
    private boolean black = false;
    private boolean white = false;
    private boolean observer = false;
    static String row;
    static String col;
    ChessGame chessGame = new ChessGame();

    private static void drawHeaders(PrintStream out) {
        setBlack(out);
        String[] headers = { "   ", " a ", " b ", " c "," d "," e ", " f ", " g ", " h "};
        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        out.println();
    }
    private static void drawReversedHeaders(PrintStream out) {
        setBlack(out);
        String[] headers = { "   ", " h ", " g ", " f "," e "," d ", " c ", " b ", " a "};
        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        out.println();
    }
    private static void drawHeader(PrintStream out, String headerText) {
        printHeaderText(out, headerText);
    }
    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_MAGENTA);
        out.print(player);
        setBlack(out);
    }
    public GameplayUi(String authToken, ServerFacade server, ChessGame.TeamColor joinType, Integer gameId, String serverUrl) {
        this.authToken = authToken;
        this.server = server;
        this.joinType = joinType;
        this.gameId = gameId;
        this.serverUrl = serverUrl;
    }
    public void run() throws ResponseException {

        WebSocketFacade ws = new WebSocketFacade(this.serverUrl, this);
        if (joinType == ChessGame.TeamColor.WHITE || joinType == ChessGame.TeamColor.BLACK)
            ws.joinPlayer(this.authToken, this.joinType, this.gameId);
        else
            ws.joinObserver(this.authToken, null, gameId);

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        this.client = new GameplayClient(this.authToken, this.gameId, this.joinType, ws, out);

        out.print(ERASE_SCREEN);
        System.out.println(SET_TEXT_COLOR_MAGENTA + "\nWelcome to your game!\n\n");

        if(this.joinType == ChessGame.TeamColor.WHITE || this.joinType == null){
            drawChessBoard(out, chessGame, false, false, true);
            if (this.joinType == ChessGame.TeamColor.WHITE) {
                white = true;
            } else {
                observer = true;
            }
        } else if(this.joinType == ChessGame.TeamColor.BLACK) {
            drawChessBoard(out, chessGame, false, true, true);
            black = true;
        }

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = this.client.eval(line);
                System.out.print(SET_TEXT_COLOR_MAGENTA + result);
                String newResult = result.replaceAll("\\s*\\b[[a-h]^[1-8]]\\b\\s*", "");
                String moveResult = newResult.replaceAll("[a-z]+$", "");
                if(result.equals("Left game\n")) {
                    return;
                } else if(result.equals("Board successfully redrawn!\n")) {
                    if(observer == true || white == true) {
                        drawChessBoard(out, chessGame, false, false, false);
                    } else if(black == true) {
                        drawChessBoard(out, chessGame, false, true, false);
                    }
                }else if(newResult.equals("Legal moves for piece:")) {
                    String[] values = result.split("\\s+");
                    int counter = 1;
                    //String row;
                    //String col;
                    for(int i = 0; i < values.length; i++) {
                        if(counter == 5) {
                            row = values[i];
                        } else if(counter == 6) {
                            col = values[i];
                        }
                        counter++;
                    }
                    if(observer == true || white == true) {
                        drawChessBoard(out, chessGame, true, false, false);
                    } else if(black == true) {
                        drawChessBoard(out, chessGame, true, true, false);
                    }
                } else if(moveResult.equals("You movedtoposition. Promotion: ")){
                    System.out.println("\n");
                    String[] values = result.split("\\s+");
                    int counter = 1;
                    int firstPositionRow = 0;
                    int firstPositionCol = 0;
                    int movePositionRow = 0;
                    int movePositionCol = 0;
                    String promotionChoice = null;
                    ChessPiece.PieceType promotion = null;
                    for(int i = 0; i < values.length; i++) {
                        if(counter == 3) {
                            firstPositionRow = Integer.parseInt(values[i]);
                        } else if (counter == 4) {
                            firstPositionCol = charToNumber(values[i]);
                        } else if(counter == 6) {
                            movePositionRow = Integer.parseInt(values[i]);
                        }else if (counter == 7) {
                            movePositionCol = charToNumber(values[i]);
                        } else if(counter == 9) {
                            promotionChoice = values[i];
                            switch(promotionChoice) {
                                case "knight" -> promotion = ChessPiece.PieceType.KNIGHT;
                                case "queen" -> promotion = ChessPiece.PieceType.QUEEN;
                                case "rook" -> promotion = ChessPiece.PieceType.ROOK;
                                case "bishop" -> promotion = ChessPiece.PieceType.BISHOP;
                                default -> promotion = null;
                            }
                        }
                        counter++;
                    }
                    ChessMove move = new ChessMove(new ChessPosition(firstPositionRow, firstPositionCol),
                            new ChessPosition(movePositionRow, movePositionCol), promotion);
                    ws.makeMove(authToken, joinType, gameId, move);
//                    if(observer == true || white == true) {
//                        drawChessBoard(out, chessGame, false, false, false);
//                    } else if(black == true) {
//                        drawChessBoard(out, chessGame, false, true, false);
//                    }
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();

    }
    public void notify(String message) {
        out.println(SET_TEXT_COLOR_RED + message);
        printPrompt();
    }
    private void printPrompt() {
        out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_WHITE);
    }


    public static void drawChessBoard(PrintStream out, ChessGame chessGame, boolean highlightMoves, boolean reverse, boolean join) throws ResponseException {
        if(join) {
            ChessBoard chessBoard = chessBoardHelper(chessGame);
        }
        ChessBoard chessBoard= chessGame.getBoard();
        if(reverse == true) {
            drawReversedHeaders(out);
        } else {
            drawHeaders(out);
        }
        int intRow = 0;
        int intCol = 0;
        boolean highlight = false;
        Collection<ChessMove> validMoves = null;
        ChessPosition position = new ChessPosition(intRow, intCol);
        if(highlightMoves == true) {
            intRow = Integer.parseInt(row);
            intCol = charToNumber(col);
            position = new ChessPosition(intRow, intCol);
            validMoves = chessBoard.getPiece(position).pieceMoves(chessBoard, position);
        }
        int startRow = reverse ? 0 : BOARD_SIZE_IN_SQUARES -1;
        int endRow = reverse ? BOARD_SIZE_IN_SQUARES : -1;
        int rowIncrement = reverse ? 1 : -1;
        int startCol = reverse ? BOARD_SIZE_IN_SQUARES - 1 : 0;
        int endCol = reverse ? -1 : BOARD_SIZE_IN_SQUARES;
        int colIncrement = reverse ? -1 : 1;
        for (int row = startRow; reverse ? (row < endRow) : (row > endRow); row+=rowIncrement) { // Changed loop condition here
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            int row2print = row + 1;
            out.print(" "+ row2print +" ");
            for (int col = startCol; reverse ? (col > endCol) : (col < endCol); col += colIncrement) {
                ChessPosition currPosition = new ChessPosition(row + 1, col + 1);
                if(highlightMoves == true) {
                    for (ChessMove movePos : validMoves) {
                        if(movePos.getEndPosition().equals(currPosition)) {
                            highlight = true;
                        }
                    }
                    if (position.equals(currPosition)) {
                        highlight = true;
                    }
                }
                if ((row + col) % 2 == 0) {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            darkWhitePieceHelper(out, highlight, position, currPosition);
                        } else {
                            darkBlackPieceHelper(out, highlight, position, currPosition);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        if(highlight == true) {
                            setDarkGreen(out);
                            highlight = false;
                        } else {
                            setDarkGray(out);
                        }
                        out.print(EMPTY);
                        highlight = false;
                    }
                    highlight = false;

                } else {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            lightWhitePieceHelper(out, highlight, position, currPosition);
                        } else {
                            lightBlackPieceHelper(out, highlight, position, currPosition);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        if(highlight == true) {
                            setLightGreen(out);
                            highlight = false;
                        } else {
                            setLightGray(out);
                        }
                        out.print(EMPTY);
                        highlight = false;
                    }
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(SET_TEXT_COLOR_MAGENTA);
                    highlight = false;
                }
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            out.print(" "+ row2print +" ");
            setBlack(out);
            out.println();
        }
        if(reverse == true) {
            drawReversedHeaders(out);
        } else {
            drawHeaders(out);
        }
        out.print('\n');
    }
    private static ChessBoard chessBoardHelper(ChessGame chessGame) {
        ChessBoard newChessBoard = new ChessBoard();
        if (chessGame.getBoard() == null) {
            chessGame.setBoard(newChessBoard);
            chessGame.getBoard().resetBoard();
        }
        ChessBoard chessBoard = chessGame.getBoard();
        return chessBoard;
    }
    private static void lightBlackPieceHelper(PrintStream out, boolean highlight, ChessPosition position, ChessPosition currPosition) {
        if(highlight == true) {
            if (position.equals(currPosition)) {
                setBlackPieceHighlight(out);
            } else {
                setBlackPieceGreen(out);
            }
        } else {
            setBlackPieceLG(out);
        }
    }
    private static void darkBlackPieceHelper(PrintStream out, boolean highlight, ChessPosition position, ChessPosition currPosition) {
        if(highlight == true) {
            if (position.equals(currPosition)) {
                setBlackPieceHighlight(out);
            } else {
                setBlackPieceDGreen(out);
            }
        } else {
            setBlackPieceDG(out);
        }
    }
    private static void darkWhitePieceHelper(PrintStream out, boolean highlight, ChessPosition position, ChessPosition currPosition) {
        if(highlight == true) {
            if(position.equals(currPosition)) {
                setWhitePieceHighlight(out);
            } else {
                setWhitePieceDGreen(out);
            }
        } else {
            setWhitePieceDG(out);
        }
    }
    private static void lightWhitePieceHelper(PrintStream out, boolean highlight, ChessPosition position, ChessPosition currPosition) {
        if(highlight == true) {
            if (position.equals(currPosition)) {
                setWhitePieceHighlight(out);
            } else {
                setWhitePieceGreen(out);
            }
        } else {
            setWhitePieceLG(out);
        }
    }
    private static void printPlayer(PrintStream out, ChessBoard chessBoard, ChessPosition currPosition) {
        switch (chessBoard.getPiece(currPosition).getPieceType()) {
            case PAWN:
                out.print(PAWN);
                break;
            case ROOK:
                out.print(ROOK);
                break;
            case KING:
                out.print(KING);
                break;
            case QUEEN:
                out.print(QUEEN);
                break;
            case KNIGHT:
                out.print(KNIGHT);
                break;
            case BISHOP:
                out.print(BISHOP);
                break;
        }
    }
    private static void setWhitePieceLG(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setWhitePieceGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setWhitePieceDGreen(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlackPieceLG(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setBlackPieceGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setBlackPieceDGreen(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setBlackPieceHighlight(PrintStream out) {
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setWhitePieceHighlight(PrintStream out) {
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setBlackPieceDG(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setWhitePieceDG(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setDarkGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }
    private static void setDarkGreen(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREEN);
        //out.print();
    }
    private static void setLightGreen(PrintStream out){
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
        //out.print();
    }
    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }
    public static int charToNumber(String letter) throws ResponseException{
        // Convert lowercase letters to numbers (a=1, b=2, ..., z=26)
        try {
            switch (letter) {
                case "a":
                    return 1;
                case "b":
                    return 2;
                case "c":
                    return 3;
                case "d":
                    return 4;
                case "e":
                    return 5;
                case "f":
                    return 6;
                case "g":
                    return 7;
                case "h":
                    return 8;
                default:
                    throw new ResponseException(400, "Col doesn't exist");
            }
        } catch (Exception ex) {
            throw new ResponseException(400, "col does not exist please enter from a-h");
        }
    }
    @Override
    public void notify(ServerMessage notification) {
        out.println(SET_TEXT_COLOR_RED + notification.getMessage());
        printPrompt();
    }
    @Override
    public void loadGame(ServerMessage notification) throws ResponseException {
        this.chessGame = notification.getGame();
        if(observer == true || white == true) {
            drawChessBoard(out, chessGame, false, false, false);
        } else if(black == true) {
            drawChessBoard(out, chessGame, false, true, false);
        }
    }
    @Override
    public void error(ServerMessage notification) {
        out.println(SET_TEXT_COLOR_RED + notification.getErrorMessage());
        printPrompt();
    }

}