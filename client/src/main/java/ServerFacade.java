
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import reqRes.*; //move to shared
//make new exception

import java.io.*;
import java.net.*;
public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public AuthData register(UserData user) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, user, AuthData.class);
    }

    public void logout(String AuthToken) throws ResponseException {

        var path = "/session";
        this.makeRequest("DELETE", path, null, null);
    }
    public AuthData login(UserData user) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, user, AuthData.class);
    }
    public int createGame(String authToken, String gameName) throws ResponseException {
        var path = "/game"; //match phase 3
        //gameId?
        int gameID;
        return this.makeRequest("POST", path, authToken, gameID);
    }
    public void joinGame(ReqJoinGame joinGameObj, String authToken) throws ResponseException{
        var path = "/game";
        this.makeRequest("PUT", path, joinGameObj, null);
    }

    public GameData[] listGames() throws ResponseException {
        var path = "/pet";
        record listGameResponse(GameData[] game) {
        }
        var response = this.makeRequest("GET", path, null, listGameResponse.class);
        return response.game();
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
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
