package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.QueryHelper;
import nl.han.simon.casus.DB.QueryHelper;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.UserRequestDTO;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.SQLException;

public class UserDAO {
    private QueryHelper queryHelper;

    @Inject
    public void setQueryHelper(QueryHelper queryHelper) { this.queryHelper = queryHelper; }
    public UserRequestDTO getUserFromName(String user) {
        var result = queryHelper.executeSelectQuery("SELECT [user], [token] FROM [user] WHERE [user] = ?", getUserTokenRowMapper(), user).stream().findFirst().orElseThrow();
        return result;
    }

    public String getUserNameFromTokenString(String tokenString) {
        var result = queryHelper.executeSelectQuery("SELECT [user] FROM [user] WHERE token = ?", getUserNameRowMapper(), tokenString).stream().findFirst().orElseThrow();
        return result.getUser();
    }

    public RowMapper<UserRequestDTO> getUserNameRowMapper() {
        return (resultSet) -> {
            var user = new UserRequestDTO();
            user.setUser(resultSet.getString(1));

            return user;
        };
    }

    public RowMapper<UserRequestDTO> getUserTokenRowMapper() {
        return (resultSet) -> {
            var user = new UserRequestDTO();
            user.setUser(resultSet.getString(1));
            user.setToken(resultSet.getString(2));

            return user;
        };
    }
}
