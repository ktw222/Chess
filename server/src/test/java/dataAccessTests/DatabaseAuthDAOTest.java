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
    void getAuth() throws DataAccessException {
        AuthDAO authDAO = new DatabaseAuthDAO();
        AuthData authData = authDAO.getAuth("123");
        Assertions.assertEquals(true, true);

    }
    @Test
    void getBadAuth() throws DataAccessException {
    }

    @Test
    void deleteAuth() {
    }

    @Test
    void clearAuth() {
    }
}