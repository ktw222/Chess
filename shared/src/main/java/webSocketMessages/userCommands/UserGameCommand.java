package webSocketMessages.userCommands;

import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }
    public  UserGameCommand(String authToken, ChessGame.TeamColor playerColor, Integer gameID) { //added by me
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;


    private ChessGame.TeamColor playerColor; //added by me
    private int gameID;//added by me
    //game id and team color
    public ChessGame.TeamColor getPlayerColor() { return playerColor; }
    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
