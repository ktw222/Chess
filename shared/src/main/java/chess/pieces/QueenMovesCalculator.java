package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.pieces.BishopMovesCaclulator;
import chess.pieces.RookMovesCalculator;

import java.util.ArrayList;

public class QueenMovesCalculator {
    public ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece myPiece = board.getPiece(myPosition);
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition currPosition;
        currPosition = myPosition;

        BishopMovesCaclulator diagonal = new BishopMovesCaclulator();
        RookMovesCalculator vertical = new RookMovesCalculator();

        possibleMoves = diagonal.bishopMoves(board, myPosition);
        possibleMoves.addAll(vertical.rookMoves(board, myPosition));

        return possibleMoves;

    }
}
