package Client;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import reqRes.*; //move to shared
//make new exception
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerFacade {
    private final String serverUrl;
    public String authToken;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public AuthData register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        UserData user = new UserData(username, password, email);
        AuthData authData = this.makeRequest("POST", path, null, user, AuthData.class);
        this.authToken = authData.authToken();
        return authData;
    }

    public void logout(String authToken) throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, authToken, null, null);
    }
    public AuthData login(String username, String password) throws ResponseException {
        var path = "/session";
        UserData user = new UserData(username, password, null);
        AuthData authData = this.makeRequest("POST", path, null, user, AuthData.class);
        this.authToken = authData.authToken();
        return authData;
    }
    public int createGame(String authToken, ReqCreateGame requestObj) throws Client.ResponseException {
        var path = "/game"; //match phase 3
        return this.makeRequest("POST", path, authToken, requestObj, GameData.class).gameID();
    }
    public void joinGame(ReqJoinGame joinGameObj, String authToken) throws ResponseException {
        var path = "/game";
        this.makeRequest("PUT", path, authToken, joinGameObj, null);
    }
    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public GameData[] listGames(String authToken) throws ResponseException {
        var path = "/game";
        record ListGameResponse(GameData[] games) {
        }
        //List<GameData> game = new ArrayList<>();
        String listOfGames = null;
        ListGameResponse response = this.makeRequest("GET", path, authToken,null, ListGameResponse.class);
        return response.games();
    }

    private <T> T makeRequest(String method, String path, String authToken, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if(authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }
            writeBody(request, http);
            http.connect();
            //writeBody(request, http); //closebody
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException, ResponseException{
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            } catch(Exception ex){
                throw new ResponseException(500, ex.getMessage());
            }
        }
    }
    //read header

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
