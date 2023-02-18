package nl.han.simon.casus.DTOs;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class User {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public JsonObject incoming() {
        return Json.createObjectBuilder()
                .add("user", user)
                .add("password", password)
                .build();
    }

    public JsonObject outgoing() {
        return Json.createObjectBuilder()
                .add("token", token)
                .add("user", user)
                .build();
    }
}
