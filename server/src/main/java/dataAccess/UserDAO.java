package dataAccess;

import model.UserData;

public interface UserDAO {
    public UserData getUser(String username) throws DataAccessException;
    public UserData createUser(UserData user) throws DataAccessException;
    public void clearUsers() throws DataAccessException;
}
