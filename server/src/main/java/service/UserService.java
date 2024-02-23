package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import model.AuthData;
public class UserService {
    public String register(UserData user) throws DataAccessException{
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        if(userDAO.getUser(user.username()) == null) {
            UserData userData = userDAO.createUser(user);
            AuthData authData =authDAO.createAuth(user.username());
            return authData.authToken();
        } else {
            throw new DataAccessException("Username is taken, pick a new one.");
        }
    }
    public AuthData login(UserData user) {
        return null;
    }
    public void logout(UserData user) {}
}
