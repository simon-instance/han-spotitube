package nl.han.simon.casus.Endpoints;

import jakarta.persistence.Convert;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PlaylistResourceTest {
    private PlaylistResource sut;

    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;

    @BeforeEach
    public void setup() {
        sut = new PlaylistResource();

        mockedPlaylistService = Mockito.mock(PlaylistService.class);
        mockedTrackService = Mockito.mock(TrackService.class);

        var mockedTrackDAO = Mockito.mock(TrackDAO.class);

        var mockedPlaylistDAO = Mockito.mock(PlaylistDAO.class);
        mockedPlaylistDAO.setTrackDAO(mockedTrackDAO);

        mockedPlaylistService.setPlaylistDAO(mockedPlaylistDAO);
        mockedPlaylistService.setTrackDAO(mockedTrackDAO);
        mockedTrackService.setTrackDAO(mockedTrackDAO);

        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackService(mockedTrackService);
    }

    @Test
    public void retrievePlaylistsWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";

        var expected = new PlaylistsWrapperDTO();
        Mockito.doReturn(expected).when(mockedPlaylistService).getAllPlaylists();

        //act
        Response res = sut.playlists(tokenString);

        //assert
        assertEquals(expected, res.getEntity());
    }

    @Test
    public void retrievePlaylistTracksWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
        int playlistId = 1;
        var expected = new TrackWrapperDTO();

        Mockito.doReturn(expected).when(mockedTrackService).getTracksByPlaylist(playlistId);

        //act
        Response res = sut.playlistTracks(playlistId, tokenString);

        //assert
        assertEquals(expected, res.getEntity());
    }

    @Test
    public void addTrackToPlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";

        int playlistId = 1;
        var track = new TrackDTO();
        var expectedStatus = 303;

        //act
        Response res = sut.addTrackToPlaylist(playlistId, tokenString, track);

        //assert
        //check if redirected correctly
        assertEquals(expectedStatus, res.getStatus());
    }

    @Test
    public void updatePlaylistNameWithTokenString() {
        //arrange
        var tokenString = "1234-1234-1234";
        var convertedPlaylist = new ConvertedPlaylistDTO();

        //act
        Response res = sut.updatePlaylistName(convertedPlaylist, convertedPlaylist.getId(), tokenString);

        //assert
        //check if redirected correctly
        assertEquals(303, res.getStatus());
    }

    @Test
    public void addPlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";

        var playlist = new ConvertedPlaylistDTO();
        playlist.setOwner(true);
        playlist.setName("Sausje");

        //act
        Response res = sut.addPlaylist(playlist, tokenString);

        //assert
        //check if redirected correctly
        assertEquals(303, res.getStatus());
    }

    @Test
    public void deletePlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
        int playlistId = 1;

        //act
        Response res = sut.deletePlaylist(playlistId, tokenString);

        //assert
        //check if redirected correctly
        assertEquals(303, res.getStatus());
    }

    @Test
    public void deletePlaylistTrackWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
        int playlistId = 1;
        int trackId = 1;

        //act
        Response res = sut.deleteTrackFromPlaylist(playlistId, trackId, tokenString);

        //assert
        assertEquals(303, res.getStatus());
    }
}