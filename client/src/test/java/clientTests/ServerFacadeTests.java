package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import Client.ServerFacade;
import model.AuthData;
import Client.ResponseException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
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
}
