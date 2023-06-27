package nl.han.simon.casus.DTOs;

public class UserRequestDTO {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    private String user;
    private String token;
}
