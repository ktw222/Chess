package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class BishopMovesCaclulator {
    public ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();

        possibleMoves.addAll(calcMultMoves(board, myPosition, 1, 1));
        possibleMoves.addAll(calcMultMoves(board, myPosition, 1, -1));
        possibleMoves.addAll(calcMultMoves(board, myPosition, -1, 1));
        possibleMoves.addAll(calcMultMoves(board, myPosition, -1, -1));
        return possibleMoves;
    }
    private  ArrayList<ChessMove> calcMultMoves(ChessBoard board, ChessPosition myPosition, int incrRow, int incrCol) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        int col = myPosition.getColumn() + incrCol;
        for (int row = myPosition.getRow() + incrRow; row >= 1 && row <= 8 && col >= 1 && col <= 8; row += incrRow, col += incrCol) {
            ChessPosition movePosition = new ChessPosition(row, col);
            ChessPiece piece = board.getPiece(movePosition);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if (piece == null) {
                possibleMoves.add(actualMove);
            } else {
                if (piece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }
        return possibleMoves;
    }
}
