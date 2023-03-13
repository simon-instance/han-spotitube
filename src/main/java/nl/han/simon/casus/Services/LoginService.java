package nl.han.simon.casus.Services;

public class LoginService {
    private static final String constUsername = "bamischijf";
    private static final String constPassword = "bamischijf";

    public boolean isAuthenticated(String user, String password){
        if(constPassword.equals(password) && constUsername.equals(user)) {
            return true;
        }

        return false;
    }
}
