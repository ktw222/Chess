package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{
    final static private HashMap<String, UserData> users = new HashMap<>();
    public UserData getUser(String username) {
        return users.get(username);
    }
    public void deleteUser(String username) {
        users.remove(username);
    }
    public UserData createUser(UserData user) {
        users.put(user.username(), user);
        return user;
    }
    public void clearUsers() {
        users.clear();
    }
}
