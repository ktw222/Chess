package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.mySQL.DatabaseAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAuthDAOTest {

    @Test
    void createAuth() throws DataAccessException {
        AuthDAO authDAO = new DatabaseAuthDAO();
        AuthData authData = authDAO.createAuth("a");
        Assertions.assertEquals("a",authData.username());
    }
    @Test
    void createBadAuth() throws DataAccessException {
        AuthDAO authDAO = new DatabaseAuthDAO();
        //AuthData authData = authDAO.createAuth(null);
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuth(null));
    }

    @Test
    void getAuth() {
    }

    @Test
    void deleteAuth() {
    }

    @Test
    void clearAuth() {
    }
}