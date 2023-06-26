
package nl.han.simon.casus.Exceptions.Mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.NoSuchElementException;


@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(NoSuchElementException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Element wasn't found: " + e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}

