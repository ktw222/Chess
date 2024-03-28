package ui;

import Client.GameplayClient;
import Client.PostLoginClient;
import Client.PreLoginClient;
import Client.ServerFacade;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


import static ui.EscapeSequences.*;

public class GameplayUi {
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
    private GameplayClient client;
    //private static Random rand = new Random();


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
    public GameplayUi(ServerFacade server, String serverUrl) {
        client = new GameplayClient(server, serverUrl, this);
    }
    public void run(PreLoginClient client) {
        System.out.println(SET_TEXT_COLOR_MAGENTA + "Welcome to your game!");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = this.client.eval(line, client.authToken);
                System.out.print(SET_TEXT_COLOR_MAGENTA + result);
                if(result.equals("Leaving game")) {
                    return;
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public void notify(String message) {
        System.out.println(SET_TEXT_COLOR_RED + message);
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_WHITE);
    }


    public static void main() { //String[] args
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        ChessGame chessGame = new ChessGame();

        drawChessBoard(out, chessGame);
        drawReversedHeaders(out);

        out.print('\n');

        drawReverseChessBoard(out, chessGame);
        drawHeaders(out);


        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }


    private static void drawChessBoard(PrintStream out, ChessGame chessGame) {
        ChessBoard newChessBoard = new ChessBoard();
        if (chessGame.getBoard() == null) {
            chessGame.setBoard(newChessBoard);
            chessGame.getBoard().resetBoard();
        }
        ChessBoard chessBoard = chessGame.getBoard();
        drawReversedHeaders(out);
        for (int row = 0; row < BOARD_SIZE_IN_SQUARES; row++) {
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            int row2Print = row + 1;
            out.print(" "+ row2Print +" ");
            for (int col = BOARD_SIZE_IN_SQUARES - 1; col >= 0; col--) {
                ChessPosition currPosition = new ChessPosition(row + 1, col + 1);
                if ((row + col) % 2 == 0) {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            setWhitePieceDG(out);
                        } else {
                            setBlackPieceDG(out);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        setDarkGray(out);
                        out.print(EMPTY);
                    }

                    setBlack(out);
                } else {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            setWhitePieceLG(out);
                        } else {
                            setBlackPieceLG(out);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        setLightGray(out);
                        out.print(EMPTY);
                    }
                    setBlack(out);
                }
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            out.print(" "+ row2Print +" ");
            setBlack(out);
            out.println();
        }
    }

    private static void drawReverseChessBoard(PrintStream out, ChessGame chessGame) {
        ChessBoard newChessBoard = new ChessBoard();
        if (chessGame.getBoard() == null) {
            chessGame.setBoard(newChessBoard);
            chessGame.getBoard().resetBoard();
        }
        ChessBoard chessBoard = chessGame.getBoard();
        drawHeaders(out);
        for (int row = BOARD_SIZE_IN_SQUARES - 1; row >= 0; row--) { // Changed loop condition here
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            int row2print = row + 1;
            out.print(" "+ row2print +" ");
            for (int col = 0; col < BOARD_SIZE_IN_SQUARES; col++) {
                ChessPosition currPosition = new ChessPosition(row + 1, col + 1);
                if ((row + col) % 2 == 0) {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            setWhitePieceDG(out);
                        } else {
                            setBlackPieceDG(out);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        setDarkGray(out);
                        out.print(EMPTY);
                    }

                } else {
                    if (chessBoard.getPiece(currPosition) != null) {
                        if (chessBoard.getPiece(currPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                            setWhitePieceLG(out);
                        } else {
                            setBlackPieceLG(out);
                        }
                        printPlayer(out, chessBoard, currPosition);
                    } else {
                        setLightGray(out);
                        out.print(EMPTY);
                    }
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(SET_TEXT_COLOR_MAGENTA);
                }
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_MAGENTA);
            out.print(" "+ row2print +" ");
            setBlack(out);
            out.println();
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

    private static void setBlackPieceLG(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlackPieceDG(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }


    private static void setWhitePieceDG(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
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

    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }
}
