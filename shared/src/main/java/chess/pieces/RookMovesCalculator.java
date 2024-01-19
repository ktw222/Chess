package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMovesCalculator {
    public ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        //moveVerticallyUp (only rows change)
        int col = myPosition.getColumn();
        for (int row = (myPosition.getRow() +1); row <= 8; row++) {

            ChessPosition movePosition;
            movePosition = new ChessPosition(row,col);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if(board.getPiece(movePosition) == null){
                possibleMoves.add(actualMove);
            }
            else {
                if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }
        //moveVerticallyDown
        for (int row = (myPosition.getRow() -1); row > 0; row--) {
            ChessPosition movePosition;
            movePosition = new ChessPosition(row,col);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if(board.getPiece(movePosition) == null){
                possibleMoves.add(actualMove);
            }
            else {
                if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }
        //moveHorizontallyRight
        int row = myPosition.getRow();
        for (int loopCol = (myPosition.getColumn() +1); loopCol <= 8; loopCol++) {

            ChessPosition movePosition;
            movePosition = new ChessPosition(row,loopCol);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if(board.getPiece(movePosition) == null){
                possibleMoves.add(actualMove);
            }
            else {
                if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }
        //moveHorizontallyLeft
        for (int loopCol = (myPosition.getColumn() -1); loopCol >0; loopCol--) {

            ChessPosition movePosition;
            movePosition = new ChessPosition(row,loopCol);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if(board.getPiece(movePosition) == null){
                possibleMoves.add(actualMove);
            }
            else {
                if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    possibleMoves.add(actualMove);
                }
                break;
            }
        }
        return possibleMoves; //placeholder
    }
}
