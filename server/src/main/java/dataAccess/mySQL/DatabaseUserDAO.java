package dataAccess.mySQL;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import dataAccess.DatabaseManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
public class DatabaseUserDAO implements UserDAO{ //extends DatabaseDAO{
    public DatabaseUserDAO() throws DataAccessException {
        configureDatabase();
    }
    public UserData createUser(UserData user) throws DataAccessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        var username = executeUpdate(statement, user.username(), hashedPassword, user.email());
        return new UserData(user.username(), user.password(), user.email());
    }
    public UserData getUser(String username) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String userString = rs.getString("username");
                        String passString = rs.getString("password");
                        String emString = rs.getString("email");
                        return new UserData(userString, passString, emString);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read data");
        }
        return null;
    }
    private UserData readUser(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        //var json = rs.getString("json");
        //var user = new Gson().fromJson(json, UserData.class);
        return null;
    }
    public void clearUsers() throws DataAccessException {
        var statement = "TRUNCATE user";
        executeUpdate(statement);
    }
    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var db = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) db.setString(i + 1, p);
                    //else if (param instanceof Integer p) db.setInt(i + 1, p);
                    //else if (param instanceof PetType p) db.setString(i + 1, p.toString());
                    else if (param == null) db.setNull(i + 1, NULL);
                }
                db.executeUpdate();
                var rs = db.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }//databaseDao-> protected
            //extend DatabaseDAO
        } catch (SQLException e) {
            throw new DataAccessException("unable to update database");
        }
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
              INDEX(password),
              INDEX(email)
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