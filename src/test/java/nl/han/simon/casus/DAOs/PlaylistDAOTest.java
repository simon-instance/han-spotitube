package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import nl.han.simon.casus.Services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistDAOTest {
    private PlaylistDAO playlistDAO;
    private Database mockedDatabase;

    // testdata
    private String username;

    @BeforeEach
    public void setup() {
        playlistDAO = new PlaylistDAO();
        mockedDatabase = Mockito.mock(Database.class);

        playlistDAO.setDatabase(mockedDatabase);

        // Test data
        username = "john_doe";
    }

    @Test
    public void getPlaylists() throws SQLException {
        // Arrange
        PlaylistsWrapperDTO<ConvertedPlaylistDTO> playlists = new PlaylistsWrapperDTO<ConvertedPlaylistDTO>();

        when(playlistDAO.getPlaylists(username)).thenReturn(playlists);

        // Act/Assert
        assertEquals(playlists, playlistDAO.getPlaylists(username));
    }
}