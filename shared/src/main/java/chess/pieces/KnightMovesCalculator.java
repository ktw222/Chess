package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMovesCalculator {
    public ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        //up 2 right 1
        int col = myPosition.getColumn()+1;
        int row = myPosition.getRow()+2;
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
        // up 2 left 1
        col = myPosition.getColumn()-1;
        row = myPosition.getRow()+2;
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
        //down 2 right 1
        col = myPosition.getColumn()+1;
        row = myPosition.getRow()-2;
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
        //down 2 left 1
        col = myPosition.getColumn()-1;
        row = myPosition.getRow()-2;
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
        //up 1 right 2
        col = myPosition.getColumn()+2;
        row = myPosition.getRow()+1;
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
        //up 1 left 2
        col = myPosition.getColumn()-2;
        row = myPosition.getRow()+1;
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
        //down 1 right 2
        col = myPosition.getColumn()+2;
        row = myPosition.getRow()-1;
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
        //down 1 left 2
        col = myPosition.getColumn()-2;
        row = myPosition.getRow()-1;
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

        return possibleMoves;
    }
}
