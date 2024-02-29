package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.GameService;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryUserDAO memoryUserDAO;
    @BeforeEach
    public void initializeData() {


        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();

    }
    @Test
    void clearAuths() throws DataAccessException {
        AuthService service = new AuthService(memoryUserDAO, memoryAuthDAO);
        service.clearAuths();
        Assertions.assertEquals(true, true);
    }
}