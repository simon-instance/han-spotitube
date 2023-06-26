package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TrackDAOTest {
    private TrackDAO sut;
    private Database mockedDatabase;

    private List<TrackDTO> tracks;

    private final int PLAYLIST_ID = 1;
    private final int TRACK_ID = 1;
    private final String PLAYLIST_NAME = "testPlaylist";
    private final String USER = "john_doe";


    @BeforeEach
    public void setup() {
        // Arrange
        mockedDatabase = Mockito.mock(Database.class);
        sut = new TrackDAO();
        sut.setDatabase(mockedDatabase);

        var track = new TrackDTO();
        track.setId(TRACK_ID);

        tracks = new ArrayList<>();
        tracks.add(track);
    }

    @Test
    public void getPlaylistTracks() {
        // Arrange
        Mockito.doReturn(tracks).when(mockedDatabase).executeSelectQuery(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(),
            ArgumentMatchers.anyInt()
        );
        // Act/Assert
        assertEquals(tracks, sut.getPlaylistTracks(PLAYLIST_ID));
    }

    @Test
    public void getAllTracksExcept() {
        // Arrange
        var trackWrapper = new TrackWrapperDTO();
        trackWrapper.setTracks(tracks);
        Mockito.doReturn(tracks).when(mockedDatabase).executeSelectQuery(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.anyInt()
        );
        // Act/Assert
        assertEquals(tracks, sut.getAllTracksExcept(PLAYLIST_ID).getTracks());
    }

    @Test
    public void getAllTracks() {
        // Arrange
        var trackWrapper = new TrackWrapperDTO();
        trackWrapper.setTracks(tracks);
        Mockito.doReturn(tracks).when(mockedDatabase).executeSelectQuery(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any()
        );
        // Act/Assert
        assertEquals(tracks, sut.getAllTracks().getTracks());
    }

    @Test
    public void deleteFromPlaylist() {
        // Act
        sut.deleteTrackFromPlaylist(TRACK_ID, PLAYLIST_ID);
        // Assert
        Mockito.verify(mockedDatabase).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }
}
