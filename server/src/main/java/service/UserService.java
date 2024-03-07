package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.UserData;
import model.AuthData;
public class UserService {
    private final UserDAO userDAO;
    private final MemoryAuthDAO memAuthDAO;

    public UserService(UserDAO userDAO, MemoryAuthDAO memAuthDAO) {
        this.userDAO = userDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public AuthData register(UserData user) throws DataAccessException{

        if(userDAO.getUser(user.username()) == null) {
            UserData newUser = userDAO.createUser(user);
            AuthData authentication = memAuthDAO.createAuth(user.username());
            return authentication;
            //could return auth token
        } else {
            throw new DataAccessException("Username is taken, pick a new one.");
        }

    }
    public void clearUsers() throws DataAccessException {
        userDAO.clearUsers();
    }
    public AuthData login(UserData user) throws DataAccessException {
        if(userDAO.getUser(user.username()) != null) {
            UserData currUser = userDAO.getUser(user.username()); //verify username and password
            if (user.password().equals(currUser.password())) {
                AuthData authentication = memAuthDAO.createAuth(user.username());
                return authentication;
            }
            //could return auth token
            else {
                throw new DataAccessException("Incorrect password");
            }
        }else {
            throw new DataAccessException("User doesn't exist");
        }
    }
    public void logout(String authToken) throws DataAccessException {
        if(memAuthDAO.getAuth(authToken) != null) {
            //AuthData currAuth = memAuthDAO.getAuth(authToken);
            memAuthDAO.deleteAuth(authToken);
            //could return auth token

        }
        else {
            throw new DataAccessException("Not Authorized");
        }
    }
}
