package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO sut;
    private Database mockedDatabase;
    private String username;
    private String tokenString;

    @BeforeEach
    public void setup() {
        mockedDatabase = Mockito.mock(Database.class);

        sut = new UserDAO();
        sut.setDatabase(mockedDatabase);

        username = "john_doe";
        tokenString = "1234-1234-1234";
    }

    @Test
    public void getUserFromName() {
        // Arrange
        var user = new UserRequestDTO();
        List<UserRequestDTO> users = new ArrayList<>();
        users.add(user);
        Mockito.doReturn(users).when(mockedDatabase).executeSelectQuery(Mockito.anyString(), Mockito.any(), Mockito.anyString());
        // Act/Assert
        assertEquals(user, sut.getUserFromName(username));
    }

    @Test
    public void getUserNameFromTokenString() {
        // Arrange
        var user = new UserRequestDTO();
        user.setUser(username);
        List<UserRequestDTO> users = new ArrayList<>();
        users.add(user);
        Mockito.doReturn(users).when(mockedDatabase).executeSelectQuery(Mockito.anyString(), Mockito.any(), Mockito.anyString());
        // Act/Assert
        assertEquals(username, sut.getUserNameFromTokenString(tokenString));
    }
}
