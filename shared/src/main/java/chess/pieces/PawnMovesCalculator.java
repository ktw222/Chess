package chess.pieces;
import chess.*;

import java.util.ArrayList;
public class PawnMovesCalculator {
    public ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalculator move = new MoveCalculator();
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        int row = 0;
        int col = 0;
        //white (moves up)
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            //in row 2 (move up 2) //can never be a promotion
            if(myPosition.getRow() == 2) {
                row = myPosition.getRow() + 2;
                col = myPosition.getColumn();
                ChessPosition movePosition;
                movePosition = new ChessPosition(row, col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                ChessPosition moveOnePosition;
                moveOnePosition = new ChessPosition(row - 1, col);
                if (board.getPiece(movePosition) == null && board.getPiece(moveOnePosition) == null) {
                    possibleMoves.add(actualMove);
                } // can move only if space is empty
            }
            //move up 1 + promotions if needed
            row = myPosition.getRow() + 1;
            col = myPosition.getColumn();
            ChessPosition movePosition;
            movePosition = new ChessPosition(row, col);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if (board.getPiece(movePosition) == null) {
                if (movePosition.getRow() == 8) {
                    move.promotion(myPosition, movePosition, actualMove, possibleMoves);
                }
                else {
                    possibleMoves.add(actualMove);
                }
            } // can move only if space is empty
            //diagonal up right capture + promotions if needed
            col = myPosition.getColumn()+1;
            row = (myPosition.getRow()+1);
            if (col <= 8) {
                move.pawnMove(row, col, myPosition, board, move, possibleMoves);
            }
            //diagonal up left capture
            col = myPosition.getColumn()-1;
            row = (myPosition.getRow()+1);
            if (col > 0) {
                move.pawnMove(row, col, myPosition, board, move, possibleMoves);
            }
        }
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            //in row 7 (move down 2) //can never be a promotion
            if(myPosition.getRow() == 7) {
                row = myPosition.getRow() - 2;
                col = myPosition.getColumn();
                ChessPosition movePosition;
                movePosition = new ChessPosition(row, col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                ChessPosition moveOnePosition;
                moveOnePosition = new ChessPosition(row + 1, col);
                if (board.getPiece(movePosition) == null && board.getPiece(moveOnePosition) == null) {
                    possibleMoves.add(actualMove);
                } // can move only if space is empty
            }
            //move up 1 + promotions if needed
            row = myPosition.getRow() - 1;
            col = myPosition.getColumn();
            ChessPosition movePosition;
            movePosition = new ChessPosition(row, col);
            ChessMove actualMove = new ChessMove(myPosition, movePosition);
            if (board.getPiece(movePosition) == null) {
                if (movePosition.getRow() == 1) {
                    move.promotion(myPosition, movePosition, actualMove, possibleMoves);
                }
                else {
                    possibleMoves.add(actualMove);
                }
            } // can move only if space is empty
            //diagonal down right capture
            col = myPosition.getColumn()+1;
            row = (myPosition.getRow()-1);
            if (col <= 8) {
                move.pawnMove(row, col, myPosition, board, move, possibleMoves);
            }
            //diagonal down left capture
            col = myPosition.getColumn()-1;
            row = (myPosition.getRow()-1);
            if (col > 0) {
                move.pawnMove(row, col, myPosition, board, move, possibleMoves);
            }
        }
        return possibleMoves;
    }
}
