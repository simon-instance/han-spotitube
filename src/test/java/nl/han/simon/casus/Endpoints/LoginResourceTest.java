package nl.han.simon.casus.Endpoints;

import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DTOs.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class LoginResourceTest {
    private LoginResource sut;

    private LoginService mockedLoginService;

    @BeforeEach
    public void setup() {
        sut = new LoginResource();

        mockedLoginService = mock(LoginService.class);

        sut.setLoginService(mockedLoginService);
    }

    @Test
    public void userIncorrectBothFields() {
        // arrange
        var loginDTO = new LoginDTO();

        loginDTO.setUser("invalidName");
        loginDTO.setPassword("invalidPassword");

        Mockito.when(mockedLoginService.isAuthenticated(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        // act
        Response res = sut.login(loginDTO);

        // assert
        assertEquals(401, res.getStatus());
    }

    @Test
    public void userCorrectBothFields() {
        // arrange
        var loginDTO = new LoginDTO();

        loginDTO.setUser("bamischijf");
        loginDTO.setPassword("bamischijf");

        Mockito.when(mockedLoginService.isAuthenticated(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        // act
        var res = sut.login(loginDTO);

        // assert
        assertEquals(200, res.getStatus());
    }
}