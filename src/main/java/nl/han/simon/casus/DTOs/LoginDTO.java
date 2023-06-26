package nl.han.simon.casus.DTOs;

public class LoginDTO {


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String user;
    private String password;
}
