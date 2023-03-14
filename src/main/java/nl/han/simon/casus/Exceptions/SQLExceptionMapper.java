package nl.han.simon.casus.Exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database error occured: " + e.getMessage())
                .build();
    }
}
