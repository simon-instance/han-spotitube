package nl.han.simon.casus.DB;

import nl.han.simon.casus.DTOs.PlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QueryHelperTests {
    private QueryHelper sut;
    private QueryHelper sutSpy;
    private Database databaseMock;

    private Connection connectionMock;
    @BeforeEach
    public void setup(){
        databaseMock = Mockito.mock(Database.class);
        connectionMock = Mockito.mock(Connection.class);
        sut = new QueryHelper(databaseMock);
        sutSpy = Mockito.spy(sut);
    }

    @Test
    public void executeSelectQuery() throws SQLException {

        when(databaseMock.getConnection()).thenReturn(connectionMock);

        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatementMock);
        // Arrange
        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTO());
        // Act
        Mockito.doReturn(playlists).when(sutSpy).executePreparedStatement(Mockito.anyString(), Mockito.isA(RowMapper.class));
        // Assert
        assertEquals(playlists, sutSpy.executeSelectQuery(ArgumentMatchers.anyString(), ArgumentMatchers.isA(RowMapper.class)));
    }
}
