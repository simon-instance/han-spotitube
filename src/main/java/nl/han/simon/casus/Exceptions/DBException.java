package nl.han.simon.casus.Exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}
