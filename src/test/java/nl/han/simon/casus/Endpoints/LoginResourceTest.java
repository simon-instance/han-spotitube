package nl.han.simon.casus.Endpoints;

import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DTOs.LoginDTO;
import nl.han.simon.casus.DTOs.TokenDTO;
import nl.han.simon.casus.Services.LoginService;
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

        loginDTO.setUser("john_doe");
        loginDTO.setPassword("123456");

        var expected = new TokenDTO();
        expected.setUser("john_doe");
        expected.setToken("1234-1234-1234");

        Mockito.when(mockedLoginService.isAuthenticated(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.doReturn(expected).when(mockedLoginService).getUserFrom(Mockito.anyString());
        // act
        var res = sut.login(loginDTO);

        // assert
       assertEquals(expected, res.getEntity());
    }
}