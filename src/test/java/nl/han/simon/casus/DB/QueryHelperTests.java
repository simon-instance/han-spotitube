package nl.han.simon.casus.DB;

import nl.han.simon.casus.DTOs.PlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QueryHelperTests {
    private Database mockedConnection;

    private Connection mockedRawConnection;

    private QueryHelper sut;

    PreparedStatement mockedStatement;

    ResultSet mockedResultSet;

    @BeforeEach
    public void setup() throws SQLException {
        mockedConnection = Mockito.mock(Database.class);
        mockedRawConnection = Mockito.mock(Connection.class);
        sut = new QueryHelper(mockedConnection);
        mockedStatement = Mockito.mock(PreparedStatement.class);
        mockedResultSet = Mockito.mock(ResultSet.class);

        Mockito.when(mockedConnection.getConnection()).thenReturn(mockedRawConnection);
        Mockito.when(mockedRawConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
    }

    @Test
    public void executeUpdateQuery_shouldExecuteUpdateQuery() throws SQLException {
        // Arrange
        String updateQuery = "UPDATE your_table SET column1 = ? WHERE id = ?";
        String col1 = "value";
        int col2 = 1;

        // Act
        sut.executeUpdateQuery(updateQuery, col1, col2);

        // Assert
        // Verify that the methods were called with the expected arguments
        Mockito.verify(mockedRawConnection).prepareStatement(updateQuery);
        Mockito.verify(mockedStatement).setObject(1, col1);
        Mockito.verify(mockedStatement).setObject(2, col2);
        Mockito.verify(mockedStatement).executeUpdate();

        // Verify that the necessary methods were called in the correct order
        Mockito.inOrder(mockedConnection, mockedRawConnection, mockedStatement);
    }

    @Test
    public void execute_shouldExecuteQuery() throws SQLException {
        // Arrange
        String selectQuery = "SELECT * FROM your_table WHERE id = ?";
        int id = 1;

        // Act
        sut.execute(selectQuery, id);

        // Assert
        Mockito.verify(mockedRawConnection).prepareStatement(selectQuery);
        Mockito.verify(mockedStatement).setObject(1, id);
        Mockito.verify(mockedStatement).execute();

        Mockito.inOrder(mockedConnection, mockedRawConnection, mockedStatement);
    }

    @Test
    public void executePreparedStatement_withResultSetData() throws SQLException {
        // Arrange
        String query = "SELECT name FROM playlist WHERE category = ?";
        RowMapper<String> rowMapper = resultSet -> resultSet.getString("name");

        // Create a list of expected data
        List<String> expectedData = Arrays.asList("Song 1", "Song 2", "Song 3");

        // Set up the mocked ResultSet to return the expected data
        Mockito.when(mockedResultSet.next())
                .thenReturn(true)  // First call returns true to indicate data is available
                .thenReturn(true)  // Second call returns true
                .thenReturn(true)  // Third call returns true
                .thenReturn(false); // Fourth call returns false to indicate end of data

        Mockito.when(mockedResultSet.getString("name"))
                .thenReturn(expectedData.get(0))
                .thenReturn(expectedData.get(1))
                .thenReturn(expectedData.get(2));

        Database mockedDatabase = Mockito.mock(Database.class);
        Mockito.when(mockedDatabase.getConnection()).thenReturn(mockedRawConnection);

        // Act
        List<String> actualData = sut.executePreparedStatement(query, rowMapper, "Rock");

        // Assert
        assertEquals(expectedData, actualData);
    }
}
