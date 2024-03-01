package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMovesCalculator {
    public ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        //moveVerticallyUp (only rows change)
        possibleMoves.addAll(calcRookMoves(board, myPosition, 1, 0)); // Up
        possibleMoves.addAll(calcRookMoves(board, myPosition, -1, 0)); // Down
        possibleMoves.addAll(calcRookMoves(board, myPosition, 0, 1)); // Right
        possibleMoves.addAll(calcRookMoves(board, myPosition, 0, -1)); // Left
        return possibleMoves;
    }
    public ArrayList<ChessMove> calcRookMoves(ChessBoard board, ChessPosition myPosition, int rowIncrement, int colIncrement) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        while (row + rowIncrement >= 1 && row + rowIncrement <= 8 && col + colIncrement >= 1 && col + colIncrement <= 8) {
            row += rowIncrement;
            col += colIncrement;
            ChessPosition movePosition = new ChessPosition(row, col);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if (board.getPiece(movePosition) == null) {
                possibleMoves.add(actualMove);
            } else {
                if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }

        return possibleMoves;
    }
}
