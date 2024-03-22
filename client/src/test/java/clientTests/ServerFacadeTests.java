package clientTests;

import org.junit.jupiter.api.*;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import server.Server;
import Client.ServerFacade;
import model.AuthData;
import Client.ResponseException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;


    @BeforeAll
    public static void init() throws ResponseException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
        if(serverFacade != null) {
            serverFacade.clear();
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }
    @Test
    void register() throws Exception {
        AuthData authData = serverFacade.register("player1", "password", "p1@email.com");
        Assertions.assertTrue(authData.authToken().length() > 10);
    }
    @Test
    void badRegister() throws ResponseException {
        //AuthData authData = serverFacade.register("me", null, null);
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.register("me", null, null));
    }
    @Test
    void login() throws Exception {
        serverFacade.register("Tyler", "myPassword", "email@email.com");
        AuthData authData = serverFacade.login("Tyler", "myPassword");
        Assertions.assertTrue(authData.authToken().length() > 10);
    }
    @Test
    void badLogin() throws ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.login("lol", "thisIsFake"));
    }
    @Test
    void logout() throws Exception {
        AuthData authData =  serverFacade.register("frog", "ribbit", "frog@lilypad.com");
        //serverFacade.logout(authData.authToken());
        Assertions.assertDoesNotThrow(() -> serverFacade.logout(authData.authToken()));
    }
    @Test
    void badLogout() throws ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.logout("5"));
    }
    @Test
    void createGame() throws ResponseException {
        AuthData authData = serverFacade.register("bee", "bzzz", "stinger@bumble.com");

        Assertions.assertDoesNotThrow(() -> serverFacade.createGame(authData.authToken(), new ReqCreateGame("beeGame")));
    }
    @Test
    void badCreateGame() throws ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.createGame("0", new ReqCreateGame("game")));
    }
    @Test
    void joinGame() throws ResponseException {
        AuthData authData = serverFacade.register("fishboy", "luca", "luca@italy.com");
        int gameID = serverFacade.createGame(authData.authToken(), new ReqCreateGame("lucaGame"));
        Assertions.assertDoesNotThrow(() -> serverFacade.joinGame(new ReqJoinGame(gameID, "WHITE"), authData.authToken()));
    }
    @Test
    void badJoinGame() throws ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.joinGame(new ReqJoinGame(2, "WHITE"), "0"));
    }
    @Test
    void listGames() throws ResponseException {
        AuthData authData = serverFacade.register("flor", "skunk", "flower@bambi.com");
        int gameID1 = serverFacade.createGame(authData.authToken(), new ReqCreateGame("stinkySkunkGame"));
        int gameID2 = serverFacade.createGame(authData.authToken(), new ReqCreateGame("otherGame"));
        Assertions.assertDoesNotThrow(() -> serverFacade.listGames(authData.authToken()));
    }
    @Test
    void badListGames() throws ResponseException {
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.listGames("0"));
    }
    @Test
    void clear() throws ResponseException {
        Assertions.assertDoesNotThrow(() -> serverFacade.clear());
    }

}
