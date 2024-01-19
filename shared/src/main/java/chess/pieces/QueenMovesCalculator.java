package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMovesCalculator {
    public ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece myPiece = board.getPiece(myPosition);
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition currPosition;
        currPosition = myPosition;

        //move up and to the right
        int col = myPosition.getColumn()+1;
        for(int row = (myPosition.getRow()+1) ; row<=8; row++) { //iterates through each row
            //for (int j = (myPosition.getColumn()+1); j<8; j++){ //iterates through each column in specified row
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
        //moveVerticallyUp (only rows change)
        col = myPosition.getColumn();
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

        return possibleMoves;

    }
}
