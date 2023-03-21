package nl.han.simon.casus.Endpoints;

import jakarta.persistence.Convert;
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

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        assertEquals(200, res.getStatus());
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
    public void retrievePlaylistTracksWithoutTokenString() {
        //arrange
        String tokenString = null;
        int playlistId = 1;

        //act
        Response res = sut.playlistTracks(playlistId, tokenString);

        //assert
        assertEquals(403, res.getStatus());
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
        assertEquals(303, res.getStatus());
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
        assertEquals(403, res.getStatus());
    }

    @Test
    public void updatePlaylistNameWithTokenString() {
        //arrange
        var tokenString = "1234-1234-1234";

        //act
        var convertedPlaylist = new ConvertedPlaylistDTO();
        convertedPlaylist.setId(1);
        convertedPlaylist.setName("testPlaylist");

        Response res = sut.updatePlaylistName(convertedPlaylist, convertedPlaylist.getId(), tokenString);

        //assert
        assertEquals(303, res.getStatus());
    }

    @Test
    public void updatePlaylistNameWithoutTokenString() {
        //arrange
        String tokenString = null;

        //act
        var convertedPlaylist = new ConvertedPlaylistDTO();
        convertedPlaylist.setId(1);
        convertedPlaylist.setName("testPlaylist");

        Response res = sut.updatePlaylistName(convertedPlaylist, convertedPlaylist.getId(), tokenString);

        //assert
        assertEquals(403, res.getStatus());
    }

    @Test
    public void addPlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
//
//        var tracks = IntStream.range(0, 9).mapToObj(i -> {
//            var track = new TrackDTO();
//
//            track.setDuration(10);
//            track.setPlaycount(10);
//            track.setTitle("Good Day" + i);
//            track.setAlbum("Im Gone" + i);
//            track.setPerformer("Iann Dior" + i);
//            track.setOfflineAvailable(false);
//            track.setPublicationDate("12-12-200" + i);
//
//            return track;
//        }).collect(Collectors.toList());

        var playlist = new ConvertedPlaylistDTO();
        playlist.setOwner(true);
        playlist.setName("Sausje");

        //act
        Response res = sut.addPlaylist(playlist, tokenString);

        //assert
        assertEquals(303, res.getStatus());
    }

    @Test
    public void addPlaylistWithoutTokenString() {
        //arrange
        String tokenString = null;

        var playlist = new ConvertedPlaylistDTO();
        playlist.setOwner(true);
        playlist.setName("Sausje");

        //act
        Response res = sut.addPlaylist(playlist, tokenString);

        //assert
        assertEquals(403, res.getStatus());
    }


    @Test
    public void deletePlaylistWithTokenString() {
        //arrange
        String tokenString = "1234-1234-1234";
        int playlistId = 1;

        //act
        Response res = sut.deletePlaylist(playlistId, tokenString);

        //assert
        assertEquals(303, res.getStatus());
    }

    @Test
    public void deletePlaylistWithoutTokenString() {
        //arrange
        String tokenString = null;
        int playlistId = 1;

        //act
        Response res = sut.deletePlaylist(playlistId, tokenString);

        //assert
        assertEquals(403, res.getStatus());
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

    @Test
    public void deletePlaylistTrackWithoutTokenString() {
        //arrange
        String tokenString = null;
        int playlistId = 1;
        int trackId = 1;

        //act
        Response res = sut.deleteTrackFromPlaylist(playlistId, trackId, tokenString);

        //assert
        assertEquals(403, res.getStatus());
    }
}