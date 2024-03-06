package dataAccess.mySQL;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.UserData;
import dataAccess.DatabaseManager;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class DatabaseUserDAO implements AuthDAO {
    public UserData createUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO pet (name, type, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(user);
        var id = executeUpdate(statement, user, json);
        return new UserData(user.username(), user.password(), user.email());
    }
    public UserData getUser(String username) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, json FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                //ps.setInt(1, id);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
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
        var json = rs.getString("json");
        var user = new Gson().fromJson(json, UserData.class);
        return user;
    }

}
