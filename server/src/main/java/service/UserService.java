package service;
import dataAccess.*;
import model.UserData;
import model.AuthData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public AuthData register(UserData user) throws DataAccessException{

        if(userDAO.getUser(user.username()) == null) {
            UserData newUser = userDAO.createUser(user);
            AuthData authentication = authDAO.createAuth(user.username());
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
            //if (user.password().equals(currUser.password())) {
            if(verifyUser(currUser, user.password())){
                AuthData authentication = authDAO.createAuth(user.username());
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
        if(authDAO.getAuth(authToken) != null) {
            //AuthData currAuth = memAuthDAO.getAuth(authToken);
            authDAO.deleteAuth(authToken);
            //could return auth token
        }
        else {
            throw new DataAccessException("Not Authorized");
        }
    }
    boolean verifyUser(UserData currUser, String inputPassword) {// read the previously hashed password from the database
        var hashedPassword = currUser.password();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, hashedPassword);
    }
}
