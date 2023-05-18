package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TokenDTO;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.SQLException;

public class UserDAO {
    private Database database;

    @Inject
    public void setDatabase(Database database) { this.database = database; }
    public TokenDTO getUserFromName(String user) {
//        try {
//            var result = database.executeSelectQuery("SELECT * FROM [user] WHERE [user] = ?", user);
//
//            result.next();
//            var newUser = new TokenDTO();
//            newUser.setUser(user);
//            newUser.setToken(result.getString("token"));
//            return newUser;
//        } catch(SQLException e) {
//            throw new DBException(e.getMessage());
//        }
        return null;
    }
}
