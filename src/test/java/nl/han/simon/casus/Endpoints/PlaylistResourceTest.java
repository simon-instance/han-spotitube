package nl.han.simon.casus.Endpoints;

import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.Services.LoginService;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlaylistResourceTest {
    private PlaylistResource sut;

    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;

    @BeforeEach
    public void setup() {
        sut = new PlaylistResource();

        mockedPlaylistService = mock(PlaylistService.class);
        mockedTrackService= mock(TrackService.class);

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
        assertEquals(200, res.getStatus());
        assertEquals(res.getEntity(), mockedPlaylistService.getAllPlaylists(tokenString));

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
        assertEquals(res.getEntity(), mockedTrackService.getTracksByPlaylist(1));
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