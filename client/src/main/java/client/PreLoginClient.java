package client;

import java.util.Arrays;

import model.AuthData;
import ui.PostLoginUi;
import ui.PreLoginUi;

public class PreLoginClient {
    private String visitorName = null;
    private final client.ServerFacade server;
    private final String serverUrl;
    public String authToken = "";

    public PreLoginClient(ServerFacade server, String serverUrl) {
        this.server = server;
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "help" -> help();
                default -> help();
                case "quit" -> "quit";
            };
        } catch (client.ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws client.ResponseException {

        if (params.length >= 2) {
            String username = params[0];
            String password = params[1];
            //state = State.SIGNEDIN;
            AuthData authData = server.login(username, password);
            authToken = authData.authToken();

            return String.format("You signed in successfully.");
        }
        throw new client.ResponseException(400, "Expected: <username password>");
    }
    public String register(String... params) throws client.ResponseException {
        if (params.length >= 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            //state = State.SIGNEDIN;
            AuthData authData = server.register(username, password, email);
            authToken = authData.authToken();
            return String.format("You registered successfully.");
            //postLoginUi.run();
        }
        throw new client.ResponseException(400, "Expected: <username password email>");
    }

    public String help() {
        return """
                    - Register <username, password, email>
                    - Login <username, password>
                    - Quit
                    """;
    }

}
