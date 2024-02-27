package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import model.AuthData;
public class UserService {
    private final MemoryUserDAO memUserDAO;
    private final MemoryAuthDAO memAuthDAO;

    public UserService(MemoryUserDAO memUserDAO, MemoryAuthDAO memAuthDAO) {
        this.memUserDAO = memUserDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public AuthData register(UserData user) throws DataAccessException{

        if(memUserDAO.getUser(user.username()) == null) {
            UserData newUser = memUserDAO.createUser(user);
            AuthData authentication = memAuthDAO.createAuth(user.username());
            return authentication;
            //could return auth token
        } else {
            throw new DataAccessException("Username is taken, pick a new one.");
        }

    }

    public void clearUsers() throws DataAccessException {
        memUserDAO.clearUsers();
    }


    public AuthData login(UserData user) {
        return null;
    }
    public void logout(UserData user) {}
}
