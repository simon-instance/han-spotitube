package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import nl.han.simon.casus.DAOs.UserDAO;
import nl.han.simon.casus.DTOs.UserRequestDTO;

public class LoginService {
    private UserDAO userDAO;
    public UserRequestDTO getUserFrom(String user) {
        return userDAO.getUserFromName(user);
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
