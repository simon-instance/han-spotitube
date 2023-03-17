package nl.han.simon.casus.Services;

public class LoginService {
    private static final String constUsername = "john_doe";
    private static final String constPassword = "123456";

    public boolean isAuthenticated(String user, String password){
        if(constPassword.equals(password) && constUsername.equals(user)) {
            return true;
        }

        return false;
    }
}
