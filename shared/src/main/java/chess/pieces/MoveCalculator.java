package chess.pieces;

import chess.*;

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
    public void promotion(ChessPosition myPosition, ChessPosition movePosition, ChessMove actualMove,
                           ArrayList<ChessMove> possibleMoves) {
        actualMove = new ChessMove(myPosition, movePosition, ChessPiece.PieceType.KNIGHT);
        possibleMoves.add(actualMove);
        actualMove = new ChessMove(myPosition, movePosition, ChessPiece.PieceType.QUEEN);
        possibleMoves.add(actualMove);
        actualMove = new ChessMove(myPosition, movePosition, ChessPiece.PieceType.ROOK);
        possibleMoves.add(actualMove);
        actualMove = new ChessMove(myPosition, movePosition, ChessPiece.PieceType.BISHOP);
        possibleMoves.add(actualMove);
    }
    public void pawnMove(int row, int col, ChessPosition myPosition, ChessBoard board, MoveCalculator move,
                         ArrayList<ChessMove> possibleMoves){
        ChessPosition movePosition = new ChessPosition(row, col);
        ChessMove actualMove = new ChessMove(myPosition, movePosition);
        if (board.getPiece(movePosition) == null) {
        } else {
            if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                if ((board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK && movePosition.getRow() == 1) ||
                        (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE && movePosition.getRow() == 8)) {
                    move.promotion(myPosition, movePosition, actualMove, possibleMoves);
                } else {
                    possibleMoves.add(actualMove);
                }
            }
        }
    }


}
