package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard chessBoard;
    //private boolean isValid;
    //private Collection<ChessMove> validMoves = new ArrayList<>();

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //ChessBoard currBoard = getBoard();
        Collection<ChessMove> validMoves = new ArrayList<>();
        if(chessBoard.getPiece(startPosition) == null) {
            Collection<ChessMove> emptyCollection = new ArrayList<>();
            return emptyCollection;
        } else {
            Collection<ChessMove> possibleMoves = chessBoard.getPiece(startPosition).pieceMoves(chessBoard, startPosition);
            //for each loop
            for (ChessMove possibleMove: possibleMoves) {
                boolean check = false;

                ChessPiece myTempChessPiece = chessBoard.getPiece(possibleMove.getEndPosition());

                ChessPiece currPiece = chessBoard.getPiece(possibleMove.getStartPosition());
                chessBoard.addPiece(possibleMove.getEndPosition(), currPiece);
                chessBoard.addPiece(possibleMove.getStartPosition(), null);

                if(chessBoard.getPiece(possibleMove.getEndPosition()) == null) {
                }
                else {
                    check = isInCheck(chessBoard.getPiece(possibleMove.getEndPosition()).getTeamColor());
                }
                if(check == false) {
                    validMoves.add(possibleMove);
                    //restore
                }
                chessBoard.addPiece(possibleMove.getStartPosition(), currPiece);
                chessBoard.addPiece(possibleMove.getEndPosition(), myTempChessPiece);
//
             }
            return validMoves;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isValid = false;
        if(chessBoard.getPiece(move.getStartPosition()) == null) {
        }
        if(chessBoard.getPiece(move.getStartPosition()).getTeamColor() == getTeamTurn()) {
            ChessPiece currPiece = chessBoard.getPiece(move.getStartPosition());
            Collection<ChessMove> validMoveList = validMoves(move.getStartPosition());
            if(validMoveList.contains(move)) {
                chessBoard.addPiece(move.getEndPosition(), currPiece);
                chessBoard.addPiece(move.getStartPosition(), null);
                if(move.getPromotionPiece() != null) {
                    //add the promotion piece if it is not null
                    TeamColor pawnColor = currPiece.getTeamColor();
                    ChessPiece.PieceType promotion = move.getPromotionPiece();
                    ChessPiece promotionPiece = new ChessPiece(pawnColor, promotion);
                    chessBoard.addPiece(move.getEndPosition(), promotionPiece);
                }
                teamTurn = (teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE; //turns things around as a boolean
            } else{throw new InvalidMoveException("move not in valid moves list");}

        } else{throw new InvalidMoveException("move not in valid moves list");}
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean check = false;
        ChessPosition kingPosition = findKing(teamColor);
        if(kingPosition == null) {
            return false;
        }
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currPosition = new ChessPosition(row, col);

                if(chessBoard.getPiece(currPosition) == null) {
                }
                else {
                    if (chessBoard.getPiece(currPosition).getTeamColor() != teamColor) {
                        for(ChessMove possibleMove: chessBoard.getPiece(currPosition).pieceMoves(chessBoard, currPosition)) {
                            ChessPosition endPosition = possibleMove.getEndPosition();
                            if(endPosition.getRow() == kingPosition.getRow() && endPosition.getColumn() == kingPosition.getColumn()) {
                                check = true;
                                return check;
                            }
                        }
                    }
                }
            }
        }
        return check;
        //throw new RuntimeException("Not implemented");
    }
    private ChessPosition findKing(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currPosition = new ChessPosition(row, col);
                if(chessBoard.getPiece(currPosition) == null) {

                }
                else {
                    if(chessBoard.getPiece(currPosition).getPieceType() == ChessPiece.PieceType.KING
                        && chessBoard.getPiece(currPosition).getTeamColor() == teamColor) {
                        kingPosition = currPosition;
                    }
                }
            }
        }
        return kingPosition;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor) == true && isInStalemate(teamColor) == true) {
            return true;
        }
        else {
            return false;
        }
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for(int col = 1; col <= 8; col++) {
                ChessPosition currPosition = new ChessPosition(row, col);
                if(chessBoard.getPiece(currPosition) == null) {
                } else {
                    if(chessBoard.getPiece(currPosition).getTeamColor() == teamColor) {
                        Collection<ChessMove> validMoveList = validMoves(currPosition);
                        if(validMoveList.isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, chessBoard);
    }
}
