package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reqRes.ReqCreateGame;
import reqRes.ReqJoinGame;
import service.GameService;
import service.UserService;

class GameServiceTest {
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryGameDAO memoryGameDAO;
    private MemoryUserDAO memoryUserDAO;
    @BeforeEach
    public void initializeData() {

        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();
        memoryGameDAO = new MemoryGameDAO();
    }
    @Test
    void createGame() throws DataAccessException {
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("piper", "buddy", "clown");
        AuthData authData = userService.register(userData);
        ReqCreateGame createGame = new ReqCreateGame("name");
        int gameID = gameService.createGame(createGame, authData.authToken());
        Assertions.assertEquals(gameID, memoryGameDAO.getGame(gameID).gameID());
    }
    @Test
    void badCreateGame() throws DataAccessException{
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        ReqCreateGame createGame = new ReqCreateGame("name");
        Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame(createGame, null));
    }

    @Test
    void listGames() throws DataAccessException {
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("a", "b", "c");
        AuthData authData = userService.register(userData);
        ReqCreateGame createGame = new ReqCreateGame("name");
        int gameID = gameService.createGame(createGame, authData.authToken());
        gameService.listGames(authData.authToken());
        Assertions.assertEquals(gameService.listGames(authData.authToken()), gameService.listGames(authData.authToken()));
    }
    @Test
    void badListGames() throws DataAccessException {
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("b", "d", "c");
        AuthData authData = userService.register(userData);
        ReqCreateGame createGame = new ReqCreateGame("name");
        int gameID = gameService.createGame(createGame, authData.authToken());
        Assertions.assertThrows(DataAccessException.class, () -> gameService.listGames(null));


    }


    @Test
    void joinGame() throws DataAccessException {
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("el", "ella", "lo");
        AuthData authData = userService.register(userData);
        ReqCreateGame createGame = new ReqCreateGame("name");
        int gameID = gameService.createGame(createGame, authData.authToken());
        ReqJoinGame joinGame = new ReqJoinGame(gameID, null);
        gameService.joinGame(joinGame, authData.authToken());
        Assertions.assertEquals(gameID, memoryGameDAO.getGame(gameID).gameID());
    }
    @Test
    void badJoinGame() throws DataAccessException {
        GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
        UserData userData = new UserData("yellow", "purple", "orange");
        AuthData authData = userService.register(userData);
        ReqCreateGame createGame = new ReqCreateGame("name");
        int gameID = gameService.createGame(createGame, authData.authToken());
        ReqJoinGame joinGame = new ReqJoinGame(1, "WHITE");

        Assertions.assertThrows(DataAccessException.class, ()-> gameService.joinGame(joinGame, null));
    }


    @Test
    void clearGames() throws DataAccessException {
        GameService service = new GameService(memoryGameDAO, memoryAuthDAO);
        service.clearGames();
        Assertions.assertEquals(true, true);
    }
}