package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMovesCalculator {
    public ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        //move up one
        int col = myPosition.getColumn();
        int row = myPosition.getRow()+1;
        if(row<=8) {
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
        //move down one
        row = myPosition.getRow()-1;
        if(row>0) {
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
        //move right one
        row = myPosition.getRow();
        col = myPosition.getColumn()+1;
        if(col<=8) {
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
        //move left one
        col = myPosition.getColumn()-1;
        if(col>0) {
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
        //move up and right one
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col <= 8) {
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
        //move down and left one
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col > 0) {
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
        //move up and left one
        col = myPosition.getColumn()-1;
        row = (myPosition.getRow()+1);
        if (row <= 8) {
            if (col > 0) {
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
        //move down and right one
        col = myPosition.getColumn()+1;
        row = (myPosition.getRow()-1);
        if (row > 0) {
            if (col <= 8) {
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

        return possibleMoves; //placeholder
    }
}
