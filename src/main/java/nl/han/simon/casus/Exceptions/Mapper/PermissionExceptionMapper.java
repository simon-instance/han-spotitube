package nl.han.simon.casus.Exceptions.Mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.simon.casus.Exceptions.PermissionException;


@Provider
public class PermissionExceptionMapper implements ExceptionMapper<PermissionException> {
    @Override
    public Response toResponse(PermissionException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("A permission error occured: " + e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
