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
    private ArrayList<UserData> users;

    @BeforeEach
    public void initializeData() {
        String username = "";
        String password = "";
        String email = "";
        String authToken = "";
        userData = new UserData(username, password, email);
        authData = new AuthData(authToken, username);

        users = new ArrayList<>();
    }

    @Test
    public void registerFirstUser() throws DataAccessException {
        UserService userServiceObj = new UserService();
        username = "bob";
        password = "12345";
        email = "bobthecat@yahoo.com";
        userData = new UserData(username, password, email);


    }

}