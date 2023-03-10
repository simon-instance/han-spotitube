package nl.han.simon.casus.DTOs;

public class LoginDTO {


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void verify() throws Exception {

    }

    private String user;
    private String password;


}
