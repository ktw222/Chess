package service;

import dataAccess.*;

public class AuthService {
    private final UserDAO userDAO;
    private final MemoryAuthDAO memAuthDAO;

    public AuthService(UserDAO userDAO, MemoryAuthDAO memAuthDAO) {
        this.userDAO = userDAO;
        this.memAuthDAO = memAuthDAO;
    }
    public void clearAuths() throws DataAccessException {
        memAuthDAO.clearAuth();
    }
}
