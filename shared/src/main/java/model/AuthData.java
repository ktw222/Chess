package model;

public record AuthData(String authToken, String username) {
    //can be used as request/response
}
