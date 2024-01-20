package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {

        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //valid moves doesn't check king
        //takes into account type of piece and location of pieces on the board

        ArrayList<ChessMove> possibleMoves = null;
        PieceType currPieceType = getPieceType();
        if(currPieceType == PieceType.BISHOP) {
            BishopMovesCaclulator bishopObject = new BishopMovesCaclulator();
            possibleMoves = bishopObject.bishopMoves(board, myPosition);
        }
        if(currPieceType == PieceType.ROOK) {
            RookMovesCalculator rookObject = new RookMovesCalculator();
            possibleMoves = rookObject.rookMoves(board, myPosition);
        }
        if(currPieceType == PieceType.QUEEN) {
            QueenMovesCalculator queenObject = new QueenMovesCalculator();
            possibleMoves = queenObject.queenMoves(board, myPosition);
        }
        if(currPieceType == PieceType.KING) {
            KingMovesCalculator kingObject = new KingMovesCalculator();
            possibleMoves = kingObject.kingMoves(board, myPosition);
        }
        if(currPieceType == PieceType.KNIGHT) {
            KnightMovesCalculator knightObject = new KnightMovesCalculator();
            possibleMoves = knightObject.knightMoves(board, myPosition);
        }
        if(currPieceType == PieceType.PAWN) {
            PawnMovesCalculator pawnObject = new PawnMovesCalculator();
            possibleMoves = pawnObject.pawnMoves(board, myPosition);
        }
        return possibleMoves;
    }


}

