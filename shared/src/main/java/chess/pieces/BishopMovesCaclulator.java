package chess.pieces;

import chess.*;

import java.util.ArrayList;

public class BishopMovesCaclulator {


    public ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece myPiece = board.getPiece(myPosition);
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition currPosition;
        currPosition = myPosition;

        //move up and to the right
        int col = myPosition.getColumn()+1;
        for(int row = (myPosition.getRow()+1) ; row<=8; row++) { //iterates through each row
            //for (int j = (myPosition.getColumn()+1); j<8; j++){ //iterates through each column in specified row
                    if(col < 8){
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

            //}
        }
        //move down and to the right
        col = myPosition.getColumn()+1;
        for(int row = (currPosition.getRow()-1) ; row>0; row--) {
            //for (int j = (currPosition.getColumn()+1); j<8; j++){
            if(col <= 8){
                ChessPosition movePosition;
                movePosition = new ChessPosition(row,col);
                ChessMove actualMove = new ChessMove(myPosition, movePosition);
                if(board.getPiece(movePosition) == null){
                    possibleMoves.add(actualMove);
                    col = col+1;
                }
                else{
                    if(board.getPiece(movePosition).getTeamColor() != board.getPiece(currPosition).getTeamColor()) {
                        possibleMoves.add(actualMove);
                    }
                    break;
                }
            }
        }
        //ChessPosition moveUpLeft = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()-1);
        col = myPosition.getColumn()-1;
        for(int row = (currPosition.getRow()+1) ; row<=8; row++) {
            //for (int j = (currPosition.getColumn()-1); j>0; j--){
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
        //ChessPosition moveDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
        col = myPosition.getColumn()-1;
        for(int row = (currPosition.getRow()-1) ; row>0; row--) {
            //for (int j = (currPosition.getColumn()-1); j>0; j--){
            if(col >0){
                //while (valid == true) {//test moveUpRight
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
