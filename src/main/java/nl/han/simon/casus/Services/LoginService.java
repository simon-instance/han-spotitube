package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import nl.han.simon.casus.DAOs.UserDAO;
import nl.han.simon.casus.DTOs.TokenDTO;

public class LoginService {
    private UserDAO userDAO;
    private static final String constUsername = "john_doe";
    private static final String constPassword = "123456";

    public boolean isAuthenticated(String user, String password){
        if(constPassword.equals(password) && constUsername.equals(user)) {
            return true;
        }

        return false;
    }

    public TokenDTO getUserFrom(String user) {
        return userDAO.getUserFromName(user);
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
