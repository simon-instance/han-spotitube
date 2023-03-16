package nl.han.simon.casus.Exceptions.Mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.simon.casus.Exceptions.DBException;

@Provider
public class DBExceptionMapper implements ExceptionMapper<DBException> {
    @Override
    public Response toResponse(DBException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database error occured: " + e.getMessage())
                .build();
    }
}
