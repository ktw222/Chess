package service;

import dataAccess.*;

public class AuthService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public AuthService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public void clearAuths() throws DataAccessException {
        authDAO.clearAuth();
    }
}
