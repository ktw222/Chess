package dataAccess;


import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    final static private HashMap<String, AuthData> auth = new HashMap<>();
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        auth.put(authToken, authData);
        return authData;
    }
    public AuthData getAuth(String authToken) {
        return auth.get(authToken);
    }
    public void deleteAuth(String authToken) {
        auth.remove(authToken);
    }
    public void clearAuth() {
        auth.clear();
    }

}
