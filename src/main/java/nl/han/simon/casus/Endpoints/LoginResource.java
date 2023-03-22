package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DTOs.LoginDTO;
import nl.han.simon.casus.DTOs.TokenDTO;
import nl.han.simon.casus.Services.LoginService;

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

        var resUser = loginService.getUserFrom(user.getUser());

        return Response.ok().entity(resUser).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}