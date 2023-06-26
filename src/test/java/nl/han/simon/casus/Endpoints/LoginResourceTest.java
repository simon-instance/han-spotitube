package nl.han.simon.casus.Endpoints;

import nl.han.simon.casus.DTOs.LoginDTO;
import nl.han.simon.casus.DTOs.UserRequestDTO;
import nl.han.simon.casus.Services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void userCorrectBothFields() {
        // arrange
        var loginDTO = new LoginDTO();

        loginDTO.setUser("john_doe");
        loginDTO.setPassword("123456");

        var expected = new UserRequestDTO();
        expected.setUser("john_doe");
        expected.setToken("1234-1234-1234");

        Mockito.doReturn(expected).when(mockedLoginService).getUserFrom(Mockito.anyString());

        // Act/Assert
       assertEquals(expected, sut.login(loginDTO).getEntity());
    }
}