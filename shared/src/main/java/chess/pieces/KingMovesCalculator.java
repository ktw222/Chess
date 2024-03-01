package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
public class KingMovesCalculator {

    public ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalculator move = new MoveCalculator();
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        int col = myPosition.getColumn();
        int row = myPosition.getRow()+1;
        if(row<=8) {
            move.movePiece(possibleMoves, board, myPosition, row, col);
        }
        row = myPosition.getRow()-1;
        if(row>0) {
            move.movePiece(possibleMoves, board, myPosition, row, col);
        }
        row = myPosition.getRow();
        col = myPosition.getColumn()+1;
        if(col<=8) {
            move.movePiece(possibleMoves, board, myPosition, row, col);
        }
        col = myPosition.getColumn()-1;
        if(col>0) {
            move.movePiece(possibleMoves, board, myPosition, row, col);
        }
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col > 0) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col <= 8) {
                move.movePiece(possibleMoves, board, myPosition, row, col);
            }
        }
        return possibleMoves;
    }
}
