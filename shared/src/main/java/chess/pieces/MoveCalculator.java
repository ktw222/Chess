package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class MoveCalculator {
    public void movePiece(ArrayList possibleMoves, ChessBoard board, ChessPosition myPosition, int row, int col) {
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

}
