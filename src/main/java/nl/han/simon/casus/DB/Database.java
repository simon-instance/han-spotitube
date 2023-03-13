package nl.han.simon.casus.DB;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class Database {

    private static final String PROPERTIES_FILE = "database.properties";

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try {
            props.load(Database.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch(IOException e) {
            System.out.println("Failed to load properties file");
        }

        String url = props.getProperty("javax.persistence.jdbc.url");
        String user = props.getProperty("javax.persistence.jdbc.user");
        String password = props.getProperty("javax.persistence.jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    // Method to execute a select statement and return a ResultSet
    public static ResultSet executeSelectQuery(String selectQuery) throws SQLException {

        // Get a Connection object from the Database class
        Connection connection = Database.getConnection();

        // Prepare the statement using the Connection object
        PreparedStatement statement = connection.prepareStatement(selectQuery);

        // Execute the query and get the ResultSet
        ResultSet resultSet = statement.executeQuery();

        // Return the ResultSet
        return resultSet;
    }
}