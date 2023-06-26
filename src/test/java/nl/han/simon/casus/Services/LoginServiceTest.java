package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.UserDAO;
import nl.han.simon.casus.DTOs.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceTest {
    private LoginService sut;
    private UserDAO mockedUserDAO;

    @BeforeEach
    public void setup() {
        mockedUserDAO = Mockito.mock(UserDAO.class);
        sut = new LoginService();
        sut.setUserDAO(mockedUserDAO);
    }

    @Test
    public void getUserFrom() {
        // Arrange
        var username = "john_doe";
        var dto = new UserRequestDTO();
        dto.setUser(username);
        // Act
        Mockito.doReturn(dto).when(mockedUserDAO).getUserFromName(username);
        // Assert
        assertEquals(dto.getUser(), sut.getUserFrom(username).getUser());
    }
}
