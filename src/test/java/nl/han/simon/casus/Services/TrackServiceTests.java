package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class TrackServiceTests {
    private TrackService sut;
    private TrackDAO mockedTrackDAO;

    private final int PLAYLIST_ID = 1;
    private final int TRACK_ID = 1;
    private List<TrackDTO> tracks;

    @BeforeEach
    public void setup() {
        // Arrange
        mockedTrackDAO = Mockito.mock(TrackDAO.class);
        sut = new TrackService();
        sut.setTrackDAO(mockedTrackDAO);

        // Testdata
        tracks = new ArrayList<>();
        tracks.add(new TrackDTO());
    }

    @Test
    public void getTracksByPlaylist() {
        // Act
        Mockito.doReturn(tracks).when(mockedTrackDAO).getPlaylistTracks(ArgumentMatchers.anyInt());
        // Assert
        assertEquals(tracks, sut.getTracksByPlaylist(PLAYLIST_ID).getTracks());
    }

    @Test
    public void getTracksExcludePlaylist_None() {
        // Arrange
        var wrapper = new TrackWrapperDTO();
        wrapper.setTracks(tracks);
        // Act
        Mockito.doReturn(wrapper).when(mockedTrackDAO).getAllTracks();
        // Assert
        assertEquals(wrapper, sut.getTracksExcludePlaylist(0));
    }

    @Test
    public void getTracksExcludePlaylist() {
        // Arrange
        var wrapper = new TrackWrapperDTO();
        wrapper.setTracks(tracks);
        // Act
        Mockito.doReturn(wrapper).when(mockedTrackDAO).getAllTracksExcept(ArgumentMatchers.anyInt());
        // Assert
        assertEquals(wrapper, sut.getTracksExcludePlaylist(PLAYLIST_ID));
    }

    @Test
    public void deleteTrackFromPlaylist() {
        // Arrange
        var sutSpy = Mockito.spy(sut);
        // Act
        sutSpy.deleteTrackFromPlaylist(TRACK_ID, PLAYLIST_ID, "");
        // Assert
        Mockito.verify(sutSpy).deleteTrackFromPlaylist(TRACK_ID, PLAYLIST_ID, "");
    }
}
