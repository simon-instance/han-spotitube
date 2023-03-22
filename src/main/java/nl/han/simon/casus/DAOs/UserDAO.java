package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TokenDTO;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.SQLException;

public class UserDAO {
    public TokenDTO getUserFromName(String user) {
        try {
            var result = Database.executeSelectQuery("SELECT * FROM [user] WHERE [user] = ?", user);

            var newUser = new TokenDTO();
            newUser.setUser(user);
            newUser.setToken(result.getString("token"));
            return newUser;
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}
