package nl.han.simon.casus.Endpoints;

import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class PlaylistResourceTest {
    private PlaylistResource sut;

    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;

    @BeforeEach
    public void setup() {
        sut = new PlaylistResource();

        mockedPlaylistService = new PlaylistService();
        mockedTrackService = new TrackService();

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

        //act
        Response res = sut.playlists(tokenString);

        //assert
        assertEquals(200, res.getEntity());
    }

    @Test
    public void retrievePlaylistsWithTokenString_Service() {
        //arrange
        String tokenString = "1234-1234-1234";
        var expected = new PlaylistsWrapperDTO();

        //act
        Response res = Mockito.when(mockedPlaylistService.getAllPlaylists()).thenReturn(expected);

        //assert
        assertEquals(200, res.getEntity());
    }

    @Test
    public void retrievePlaylistsWithoutTokenString() {
        //arrange
        String tokenString = null;

        //act
        Response res = sut.playlists(tokenString);

        //assert
        assertEquals(403, res.getStatus());
    }

    @Test
    public void retrievePlaylistTracksWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
        int playlistId = 1;

        //act
        Response res = sut.playlistTracks(playlistId, tokenString);

        //assert
        assertEquals(200, res.getStatus());
    }

    @Test
    public void addTrackToPlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";

        var track = new TrackDTO();
        track.setTitle("testTrack");
        track.setOfflineAvailable(false);
        track.setPerformer("performer");
        track.setPublicationDate("2002-02-02");
        track.setPlaycount(30);
        track.setDescription("bami");
        track.setAlbum("testAlbum");

        //act
        Response res = sut.addTrackToPlaylist(1, tokenString, track);

        //assert
        assertEquals(res.getStatus(), 303);
    }

    @Test
    public void updatePlaylistNameWithTokenString() {
        //arrange
        var tokenString = "1234-1234-1234";

        //act
        var playlistDTO = new ConvertedPlaylistDTO();
        playlistDTO.setName("testPlaylist");

//        //assert
//        assertEquals();
    }

    @Test
    public void addTrackToPlaylistWithoutTokenString() {
        //arrange
        String tokenString = null;

        var track = new TrackDTO();
        track.setTitle("testTrack");
        track.setOfflineAvailable(false);
        track.setPerformer("performer");
        track.setPublicationDate("2002-02-02");
        track.setPlaycount(30);
        track.setDescription("bami");
        track.setAlbum("testAlbum");

        //act
        Response res = sut.addTrackToPlaylist(1, tokenString, track);

        //assert
        assertEquals(res.getStatus(), 403);
    }

    @Test
    public void retrievePlaylistTracksWithoutTokenString() {
        //arrange
        String tokenString = null;
        int playlistId = 1;

        //act
        Response res = sut.playlistTracks(playlistId, tokenString);

        //assert
        assertEquals(403, res.getStatus());
    }
}