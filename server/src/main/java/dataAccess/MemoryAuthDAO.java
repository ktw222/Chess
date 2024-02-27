package dataAccess;


import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    final private ArrayList<UserData> auth = new ArrayList<>();
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        return authData;
    }
    public void clearAuth() {
        auth.clear();
    }

}
