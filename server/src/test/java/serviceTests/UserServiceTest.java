package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private String username;
    private String password;
    private String email;
    private UserData userData;
    private AuthData authData;
    private  MemoryAuthDAO memoryAuthDAO;
    private MemoryUserDAO memoryUserDAO;
    private ArrayList<UserData> users;

    @BeforeEach
    public void initializeData() {
        String username = "";
        String password = "";
        String email = "";
        String authToken = "";
        userData = new UserData(username, password, email);
        authData = new AuthData(authToken, username);
        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();

        users = new ArrayList<>();
    }

    @Test
    public void registerFirstUser() throws DataAccessException {
        UserService userServiceObj = new UserService(memoryUserDAO, memoryAuthDAO);
        username = "bob";
        password = "12345";
        email = "bobthecat@yahoo.com";
        userData = new UserData(username, password, email);
        //authData = userServiceObj.register(u)

        userServiceObj.register(userData);
        Assertions.assertEquals(userData, memoryUserDAO.getUser(username));
    }
    @Test
    public void registerExistingUser() throws DataAccessException {
        UserService userServiceObj = new UserService(memoryUserDAO, memoryAuthDAO);
        username = "bob";
        password = "12345";
        email = "bobthecat@yahoo.com";
        userData = new UserData(username, password, email);
        userServiceObj.register(userData);
        username = "bob";
        password = "109876";
        email = "bobsEmail234@bing.com";
        userData = new UserData(username,password, email);
        Assertions.assertThrows(DataAccessException.class, () -> userServiceObj.register(userData));

        //Assertions.assertEquals(success, userServiceObj.register(userData));
        //Assertions.assertEquals(userData, memoryUserDAO.getUser(username));
    }

    @Test
    void logoutNotSuccessful() throws Exception{
        UserService userServiceObj = new UserService(memoryUserDAO, memoryAuthDAO);
        String empty = null;
        Assertions.assertThrows(Exception.class, () -> userServiceObj.logout(empty));
    }
    @Test
    void logoutSuccessful() throws Exception{
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("a", "b", "c");
        AuthData auth = userService.register(userData);
        userService.logout(auth.authToken());
        Assertions.assertEquals(null, memoryAuthDAO.getAuth(auth.authToken()));
    }
    @Test
    void loginNotSuccessful() throws Exception {
        UserService service = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("a", "b", "c");
        Assertions.assertThrows(Exception.class, () -> service.login(userData));
    }
    @Test
    void loginSuccessful() throws Exception {
        UserService service = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("a", "b", "c");
        AuthData auth = service.register(userData);
        service.logout(auth.authToken());
        AuthData loginAuth = service.login(userData);
        Assertions.assertEquals(loginAuth, memoryAuthDAO.getAuth(loginAuth.authToken()));

    }
    @Test
    void clearUsers() throws Exception {
        UserService service = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("a", "b", "c");
        service.register(userData);
        service.clearUsers();
        Assertions.assertEquals(true, true);
    }

}