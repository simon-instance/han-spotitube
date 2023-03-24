package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.ScriptRunner;
import nl.han.simon.casus.Services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistDAOTest {
    private PlaylistService playlistService;
    @Mock
    private PlaylistDAO playlistDAO;
    @Mock
    private PlaylistDAO trackDAO;

    @BeforeEach
    public void setup() throws SQLException, IOException {
        Connection connection = Database.getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection, true, true);
        scriptRunner.runScript(new InputStreamReader(ClassLoader.getSystemResourceAsStream("import.sql")));
        connection.close();

        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService();
    }

    @Test
    public void getPlaylists() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Playlist 1");

        when(Database.executeSelectQuery("SELECT * FROM playlist WHERE owner = ?", "john_doe")).thenReturn(resultSet);

        // Act
        var playlists = playlistService.getAllPlaylists();

        // Assert
        assertEquals(1, playlists.getPlaylists().size());
        var playlist = playlists.getPlaylists().get(0);
        assertEquals(1, playlist.getId());
        assertEquals("Playlist 1", playlist.getName());
        assertEquals(true, playlist.isOwner());
        assertNotNull(playlist.getTracks());
        assertTrue(playlist.getTracks().isEmpty());
    }
}