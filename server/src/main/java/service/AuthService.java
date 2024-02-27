package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;

public class AuthService {
    private final MemoryUserDAO memUserDAO;
    private final MemoryAuthDAO memAuthDAO;

    public AuthService(MemoryUserDAO memUserDAO, MemoryAuthDAO memAuthDAO) {
        this.memUserDAO = memUserDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public void clearAuths() throws DataAccessException {
        memAuthDAO.clearAuth();
    }
}
