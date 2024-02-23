package dataAccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO{
    final private ArrayList<UserData> users = new ArrayList<>();
    public UserData getUser(String username) {
        UserData user = null;
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).username().equals(username)) {
                user = users.get(i);
            }
        }
        return user;
    }
    public void deleteUser(UserData user) {
        users.remove(user);
    }
    public UserData createUser(UserData user) {
        user = new UserData(user.username(), user.password(), user.email());
        users.add(user);
        return user;
    }
    public void clearUsers() {
        users.clear();
    }
}
