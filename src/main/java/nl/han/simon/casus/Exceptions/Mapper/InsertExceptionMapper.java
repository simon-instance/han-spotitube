package nl.han.simon.casus.Exceptions.Mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.simon.casus.Exceptions.InsertException;

@Provider
public class InsertExceptionMapper implements ExceptionMapper<InsertException> {
    @Override
    public Response toResponse(InsertException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Insert error occured: " + e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
