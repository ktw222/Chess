package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;

public class GameService {
    private final MemoryGameDAO memGameDAO;
    private final MemoryAuthDAO memAuthDAO;

    public GameService(MemoryGameDAO memGameDAO, MemoryAuthDAO memAuthDAO) {
        this.memGameDAO = memGameDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public void clearGames() throws DataAccessException {
        memGameDAO.clearGames();
    }
}
