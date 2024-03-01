package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class BishopMovesCaclulator {
    public ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition currPosition;
        currPosition = myPosition;
        int col = myPosition.getColumn()+1;
        for(int row = (myPosition.getRow()+1) ; row<=8; row++) {
                    if(col <= 8){
                        ChessPosition movePosition;
                        movePosition = new ChessPosition(row,col);
                        ChessMove actualMove = new ChessMove(myPosition, movePosition);
                        if(board.getPiece(movePosition) == null){
                            possibleMoves.add(actualMove);
                            col = col+1;
                        }
                        else {
                            if (board.getPiece(movePosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                                possibleMoves.add(actualMove);
                            }
                            break;
                        }
                    }
        }
        col = myPosition.getColumn()+1;
        for(int row = (currPosition.getRow()-1); row>0; row--) {
            if(col <= 8){
                ChessPosition movePosition;
                movePosition = new ChessPosition(row, col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                if(board.getPiece(movePosition) == null){
                    possibleMoves.add(actualMove);
                    col = col +1;
                }
                else{
                    if(board.getPiece(movePosition).getTeamColor() != board.getPiece(currPosition).getTeamColor()) {
                        possibleMoves.add(actualMove);
                    }
                    break;
                }
            }
        }
        col = myPosition.getColumn()-1;
        for(int row = (currPosition.getRow()+1) ; row<=8; row++) {
            if (col>0) {
                ChessPosition movePosition;
                movePosition = new ChessPosition(row,col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                if(board.getPiece(movePosition) == null){
                    possibleMoves.add(actualMove);
                    col--;
                }
                else{
                    if(board.getPiece(movePosition).getTeamColor() != board.getPiece(currPosition).getTeamColor()) {
                        possibleMoves.add(actualMove);
                    }
                    break;
                }
            }
        }
        col = myPosition.getColumn()-1;
        for(int row = (currPosition.getRow()-1) ; row>0; row--) {
            if(col >0){
                ChessPosition movePosition;
                movePosition = new ChessPosition(row,col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                if(board.getPiece(movePosition) == null){
                    possibleMoves.add(actualMove);
                    col--;
                }
                else{
                    if(board.getPiece(movePosition).getTeamColor() != board.getPiece(currPosition).getTeamColor()) {
                        possibleMoves.add(actualMove);
                    }
                    break;
                }
            }
        }
        return possibleMoves;
    }

}
