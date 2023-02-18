package nl.han.simon.casus;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DB.ConnectURL;
import nl.han.simon.casus.DTOs.User;

@Path("/login")
public class Login {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        var responseUser = new User();
        responseUser.setUser(user.getUser());
        responseUser.setToken("1234-1234-1234");
        return Response.ok().entity(responseUser.outgoing()).build();
    }

}