package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;


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
    private static final String Rook = " R ";
    //private static Random rand = new Random();


    public static void main() { //String[] args
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);
        drawChessBoard(out);
        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);
        String[] headers = { "   ", " a ", " b ", " c "," d "," e ", " f ", " g ", " h "};
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
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {


        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES/2; ++boardRow) {

            drawBoard(out);


            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }

    private static void drawBoard(PrintStream out) {
        setBlack(out);
        out.print(EMPTY);
        drawDarkSquare(out);
        setBlack(out);
        out.print(EMPTY);
        drawLightSquare(out);
    }
    private static void drawDarkSquare(PrintStream out) {
        for(int counter = 0; counter < BOARD_SIZE_IN_SQUARES; counter++) {
            if (counter % 2 == 0) {
                setDarkGray(out);
                out.print(EMPTY);
            } else {
                setLightGray(out);
                out.print(EMPTY);
            }
        }
        setBlack(out);

        out.println();

    }
    private static void drawLightSquare(PrintStream out) {
        for(int counter = 0; counter < BOARD_SIZE_IN_SQUARES; counter++) {
            if (counter % 2 == 0) {
                setLightGray(out);
                out.print(EMPTY);
            } else {
                setDarkGray(out);
                out.print(EMPTY);
            }
        }
        setBlack(out);

        out.println();

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

    private static void printPlayer(PrintStream out, String player, boolean isDark, boolean teamColorWhite) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
        if(isDark == true && teamColorWhite == true) {
            out.print(SET_TEXT_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(player);
        } else if(isDark == true && teamColorWhite != true) {
            out.print(SET_TEXT_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(player);
        }else if(isDark != true && teamColorWhite != true) {
            out.print(SET_TEXT_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(player);
        }else if(isDark != true && teamColorWhite == true) {
            out.print(SET_TEXT_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(player);
        }
        //setWhite(out);
    }
}
