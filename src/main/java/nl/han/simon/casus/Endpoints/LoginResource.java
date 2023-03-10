package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DTOs.LoginDTO;
import nl.han.simon.casus.DTOs.TokenDTO;

@Path("/login")
public class LoginResource {

    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO user) {
        if(!loginService.isAuthenticated(user.getUser(), user.getPassword())) {
            return Response.status(401).build();
        }

        var responseDTO = new TokenDTO();

        responseDTO.setUser(user.getUser());
        responseDTO.setToken("1234-1234-1234");

        return Response.ok().entity(responseDTO).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}