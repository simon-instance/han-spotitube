package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.QueryHelper;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistTrackDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDAOTest {
    private PlaylistDAO sut;
    private QueryHelper mockedQueryHelper;


    // Test data
    private List<PlaylistTrackDTO> playlistTracks;
    private PlaylistDTO playlist;
    private List<PlaylistDTO> playlistWrapperData;

    private final int PLAYLIST_ID = 1;
    private final String PLAYLIST_NAME = "testPlaylist";
    private final String USER = "john_doe";

    @BeforeEach
    public void setup() {
        // Arrange
        mockedQueryHelper = Mockito.mock(QueryHelper.class);
        sut = new PlaylistDAO();
        sut.setQueryHelper(mockedQueryHelper);

        // Test data
        var playlistTrack = new PlaylistTrackDTO();
        playlistTrack.setPlaylistId(PLAYLIST_ID);

        playlistTracks = new ArrayList<>();
        playlistTracks.add(playlistTrack);

        playlist = new PlaylistDTO();
        playlist.setId(PLAYLIST_ID);
        playlist.setTracks(playlistTracks);
        playlist.setOwner(USER);

        playlistWrapperData = new ArrayList<>();
        playlistWrapperData.add(playlist);
    }

    @Test
    public void getPlaylists() {
        // Arrange
        var sutSpy = Mockito.spy(sut);

        List<ConvertedPlaylistDTO> convertedPlaylistData = new ArrayList<>();
        convertedPlaylistData.add(sutSpy.toConvertedPlaylistDTO(playlist, USER));

        PlaylistsWrapperDTO<ConvertedPlaylistDTO> convertedPlaylists = new PlaylistsWrapperDTO<>(convertedPlaylistData);

        Mockito.doReturn(playlistWrapperData).when(mockedQueryHelper).executeSelectQuery(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.isA(RowMapper.class)
        );

        Mockito.doReturn(convertedPlaylistData).when(sutSpy).toConvertedPlaylists(
                ArgumentMatchers.anyList(),
                ArgumentMatchers.anyList(),
                ArgumentMatchers.anyString()
        );

        Mockito.doReturn(playlistTracks).when(sutSpy).getPlaylistTracks();

        // Act/Assert
        assertEquals(convertedPlaylists.getPlaylists(), sutSpy.getPlaylists(USER).getPlaylists());
    }

    @Test
    public void toConvertedPlaylists() {
        // Arrange
        var sutSpy = Mockito.spy(sut);

        List<ConvertedPlaylistDTO> convertedPlaylistData = new ArrayList<>();
        ConvertedPlaylistDTO item = sutSpy.toConvertedPlaylistDTO(playlist, USER);
        convertedPlaylistData.add(item);

        Mockito.doReturn(item).when(sutSpy).toConvertedPlaylistDTO(
                ArgumentMatchers.any(),
                ArgumentMatchers.anyString()
        );

        // Act/Assert
        assertEquals(convertedPlaylistData, sutSpy.toConvertedPlaylists(playlistWrapperData, playlistTracks, USER));
    }

    @Test
    public void getPlaylistTracks() {
        // Arrange
        Mockito.doReturn(playlistTracks).when(mockedQueryHelper).executeSelectQuery(
                Mockito.anyString(),
                Mockito.any()
        );

        // Act/Assert
        assertEquals(playlistTracks, sut.getPlaylistTracks());
    }

    @Test
    public void updatePlaylistName() {
        // Act
        sut.updatePlaylistName(PLAYLIST_ID, PLAYLIST_NAME);
        // Assert
        Mockito.verify(mockedQueryHelper).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt());
    }

    @Test
    public void addTrackToPlaylist() {
        // Arrange
        var trackId = 1;
        // Act
        sut.addTrackToPlaylist(PLAYLIST_ID, trackId);
        // Assert
        Mockito.verify(mockedQueryHelper).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @Test
    public void addPlaylist() {
        // Arrange
        // Act
        sut.addPlaylist(PLAYLIST_NAME, USER);
        // Assert
        Mockito.verify(mockedQueryHelper).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void deletePlaylist() {
        // Act
        sut.deletePlaylist(PLAYLIST_ID);
        // Assert
        Mockito.verify(mockedQueryHelper).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt());
    }
}