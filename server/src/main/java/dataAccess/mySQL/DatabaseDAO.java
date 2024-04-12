package dataAccess.mySQL;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;

import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class DatabaseDAO {
    protected int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var db = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var variables = params[i];
                    if (variables instanceof String p) db.setString(i + 1, p);
                    else if (variables instanceof Integer p) db.setInt(i + 1, p);
                        //else if (param instanceof PetType p) db.setString(i + 1, p.toString());
                    else if (variables == null) db.setNull(i + 1, NULL);
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

}
