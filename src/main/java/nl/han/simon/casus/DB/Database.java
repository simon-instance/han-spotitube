package nl.han.simon.casus.DB;

import nl.han.simon.casus.Exceptions.DBException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private final String PROPERTIES_FILE = "database.properties";
    public Connection getConnection() {
        Properties props = new Properties();
        try {
            props.load(Database.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch(IOException e) {
            System.out.println("Failed to load properties file");
        }

        String devMode = props.getProperty("dev");
        try {
            if("false".equals(devMode)) {
                String url = props.getProperty("javax.persistence.jdbc.url");
                String user = props.getProperty("javax.persistence.jdbc.user");
                String password = props.getProperty("javax.persistence.jdbc.password");

                return DriverManager.getConnection(url, user, password);
            } else {
                String url = props.getProperty("url");
                return DriverManager.getConnection(url);
            }
        } catch(SQLException e) {
            throw new DBException("Failed to connect to database");
        }
    }

}
