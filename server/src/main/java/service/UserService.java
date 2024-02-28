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


    public AuthData login(UserData user) throws DataAccessException {
        if(memUserDAO.getUser(user.username()) != null) {
            UserData currUser = memUserDAO.getUser(user.username());
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
    public void logout(AuthData auth) throws DataAccessException {
        if(memAuthDAO.getAuth(auth.authToken()) != null) {
            AuthData currAuth = memAuthDAO.getAuth(auth.authToken());
            memAuthDAO.deleteAuth(auth.authToken());

            //could return auth token

        }
        else {
            throw new DataAccessException("Not Authorized");
        }
    }
}
