package nl.han.simon.casus.DB;

import jakarta.inject.Inject;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
    public Database database;

    @Inject
    public QueryHelper(Database database) {
        this.database = database;
    }


    // Method to execute a select statement and return a ResultSet
    public <T> List<T> executeSelectQuery(String selectQuery, RowMapper<T> rowMapper, Object... bindParams) {
        return executePreparedStatement(selectQuery, rowMapper, bindParams);
    }

    public void executeUpdateQuery(String updateQuery, Object... bindParams) {
        try {
            // Get a Connection object from the Database class
            Connection connection = database.getConnection();

            // Prepare the statement using the Connection object
            PreparedStatement statement = connection.prepareStatement(updateQuery);

            // Assign bind parameters to statement
            for (int i = 0; i < bindParams.length; i++) {
                statement.setObject(i + 1, bindParams[i]);
            }

            // Execute the query and get the ResultSet
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DBException("Error executing UPDATE statement");
        }
    }


    public void execute(String selectQuery, Object... bindParams) {
        try {
            // Get a Connection object from the Database class
            Connection connection = database.getConnection();

            // Prepare the statement using the Connection object
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            // Assign bind parameters to statement
            for (int i = 0; i < bindParams.length; i++) {
                statement.setObject(i + 1, bindParams[i]);
            }

            // Execute the query and get the ResultSet
            statement.execute();
        } catch(SQLException e) {
            throw new DBException("Error executing INSERT/DELETE statement");
        }
    }

    public <T extends Object> List<T> executePreparedStatement(String query, RowMapper<T> rowMapper, Object... bindParams) {
        try {
            Connection connection = database.getConnection();

            PreparedStatement statement = connection.prepareStatement(query);

            for (int i = 0; i < bindParams.length; i++) {
                statement.setObject(i + 1, bindParams[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            List<T> resultList = new ArrayList<>();

            while(resultSet.next()) {
                var mappedObj = rowMapper.mapRow(resultSet);
                resultList.add(mappedObj);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return resultList;
        } catch (SQLException e) {
            throw new DBException("Error executing prepared statement");
        }
    }
}
