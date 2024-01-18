package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCaclulator {
    private Boolean Valid = true;


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece myPiece = board.getPiece(myPosition);
        ArrayList<Object> possibleMoves = new ArrayList<>();
        ChessPosition currPosition;
        currPosition = myPosition;
        ChessPosition moveUpRight = new ChessPosition(currPosition.getRow()+1, currPosition.getColumn()+1);

        //while loop iterating through until no longer valid
        for(int i = currPosition.getRow(); i<=8; i++) {
            for (int j = currPosition.getColumn(); j<=8; j++){
                while (getValid()) {//test moveUpRight
                    if(board.getPiece(currPosition) == null){
                        setValid();
                    }
                }
            }
        }
        ChessPosition moveDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
        ChessPosition moveUpLeft = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()-1);
        ChessPosition moveDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);

        return new ArrayList<>(); //placeholder

        //a collection returns a list or set
        //I will probably return an arraylist
    }

    public Boolean getValid() {
        return Valid;
    }

    public void setValid() { //sets to be not valid
        this.Valid = true;
    }
}
