package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
public class KingMovesCalculator {
    public void moveKing(ArrayList possibleMoves, ChessBoard board, ChessPosition myPosition, int row, int col) {
        ChessPosition movePosition;
        movePosition = new ChessPosition(row, col);
        ChessMove actualMove = new ChessMove(myPosition, movePosition);
        if (board.getPiece(movePosition) == null) {
            possibleMoves.add(actualMove);
        } else {
            if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                possibleMoves.add(actualMove);
            }
        }
    }
    public ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        int col = myPosition.getColumn();
        int row = myPosition.getRow()+1;
        if(row<=8) {
            moveKing(possibleMoves, board, myPosition, row, col);
        }
        row = myPosition.getRow()-1;
        if(row>0) {
            moveKing(possibleMoves, board, myPosition, row, col);
        }
        row = myPosition.getRow();
        col = myPosition.getColumn()+1;
        if(col<=8) {
            moveKing(possibleMoves, board, myPosition, row, col);
        }
        col = myPosition.getColumn()-1;
        if(col>0) {
            moveKing(possibleMoves, board, myPosition, row, col);
        }
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col <= 8) {
                moveKing(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col > 0) {
                moveKing(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col > 0) {
                moveKing(possibleMoves, board, myPosition, row, col);
            }
        }
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col <= 8) {
                moveKing(possibleMoves, board, myPosition, row, col);
            }
        }
        return possibleMoves;
    }
}
