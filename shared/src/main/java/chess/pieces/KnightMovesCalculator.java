package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMovesCalculator {
    public ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalculator move = new MoveCalculator();
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        //up 2 right 1
        int col = myPosition.getColumn()+1;
        int row = myPosition.getRow()+2;
        if (row <= 8) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        // up 2 left 1
        col = myPosition.getColumn()-1;
        row = myPosition.getRow()+2;
        if (row <= 8) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //down 2 right 1
        col = myPosition.getColumn()+1;
        row = myPosition.getRow()-2;
        if (row > 0) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //down 2 left 1
        col = myPosition.getColumn()-1;
        row = myPosition.getRow()-2;
        if (row > 0) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //up 1 right 2
        col = myPosition.getColumn()+2;
        row = myPosition.getRow()+1;
        if (row <= 8) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //up 1 left 2
        col = myPosition.getColumn()-2;
        row = myPosition.getRow()+1;
        if (row <= 8) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //down 1 right 2
        col = myPosition.getColumn()+2;
        row = myPosition.getRow()-1;
        if (row > 0) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        //down 1 left 2
        col = myPosition.getColumn()-2;
        row = myPosition.getRow()-1;
        if (row > 0) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }

        return possibleMoves;
    }
}
