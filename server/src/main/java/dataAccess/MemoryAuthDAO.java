package dataAccess;

import model.UserData;

import java.util.ArrayList;


public class MemoryAuthDAO implements AuthDAO {
    final private ArrayList<UserData> users = new ArrayList<>();
    public UserData getUser(UserData user) {
        return user;
    }
    public UserData createUser(UserData user) {
        user = new UserData(user.username(), user.password(), user.email());
        users.add(user);
        return user;
    }

}
