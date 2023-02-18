package nl.han.simon.casus.DB;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.*;

@WebListener
public class ConnectURL implements ServletContextListener {
    private static Connection connection;

    public static Connection getConn() {
        return connection;
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ConnectURL.createConn();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) throws RuntimeException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createConn() {

        String serverName = "localhost";
        String portNumber = "1433";
        String databaseName = "Spotitube";
        String username = "applicatie";
        String password = "Bam1schijf";

        try {
            // Create a variable for the connection string.
            String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";" + "databaseName="
                    + databaseName + ";username=" + username + ";password=" + password + ";encrypt=true;trustServerCertificate=true;";
            System.out.println("\n\n\nConnecting to " + connectionUrl + " ...\n\n\n");

            // Establish the connection.
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
                ConnectURL.connection = con;
                System.out.println();
                System.out.println("Connection established successfully.");

                // Create and execute an SQL statement that returns username.
                String SQL = "SELECT SUSER_SNAME()";
                try (ResultSet rs = stmt.executeQuery(SQL)) {

                    // Iterate through the data in the result set and display it.
                    while (rs.next()) {
                        System.out.println("user name: " + rs.getString(1));
                    }
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
