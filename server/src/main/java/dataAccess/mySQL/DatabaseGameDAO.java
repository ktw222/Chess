package dataAccess.mySQL;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reqRes.ReqJoinGame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DatabaseGameDAO extends DatabaseDAO implements GameDAO {
    public DatabaseGameDAO() throws DataAccessException {
        configureDatabase();
    }
    public int createGame(String gameName) throws DataAccessException{

        ChessGame chessGame = new ChessGame();
        Gson gson = new Gson();
        String stringGame = gson.toJson(chessGame);
        var statement = "INSERT INTO user (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
        //need to serialize the game before updating it
        var username = executeUpdate(statement, null, null, gameName, stringGame);
        try (var conn = DatabaseManager.getConnection()) {
            statement = "SELECT gameID FROM game WHERE gameName=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("gameID");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read data");
        }
        return 0;
    }
    public GameData getGame(int gameID) {
        return null;
    }
    public void joinGame(ReqJoinGame joinGameObj, String username) throws DataAccessException {}
    public void clearGames() throws DataAccessException{
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }
    public Collection<GameData> listGames() throws DataAccessException{
        var listOfGames = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameId FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        //listOfGames.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read data");
        }
        return listOfGames;
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` blob NOT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName),
              INDEX(game)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private void configureDatabase() throws DataAccessException{
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to configure database");
        }
    }

}
