package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.Endpoints.PlaylistResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {
    private PlaylistResource sut;

    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;
    private PlaylistDAO mockedPlaylistDAO;

    @BeforeEach
    public void setup() {
        sut = new PlaylistResource();

        mockedPlaylistService = Mockito.mock(PlaylistService.class);
        mockedTrackService = Mockito.mock(TrackService.class);

        var mockedTrackDAO = Mockito.mock(TrackDAO.class);

        var mockedPlaylistDAO = Mockito.mock(PlaylistDAO.class);
        this.mockedPlaylistDAO = mockedPlaylistDAO;
        this.mockedPlaylistDAO.setTrackDAO(mockedTrackDAO);

        mockedPlaylistService.setPlaylistDAO(mockedPlaylistDAO);
        mockedPlaylistService.setTrackDAO(mockedTrackDAO);
        mockedTrackService.setTrackDAO(mockedTrackDAO);

        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackService(mockedTrackService);
    }

    @Test
    void getAllPlaylists() {
        //arrange

        //act


        //assert
//        Mockito.verify(mockedPlaylistService).getAllPlaylists();
    }

    @Test
    void updatePlaylistName() {
        //arrange
        String token = "1234-1234-1234";
        int playlistId = 3;
        var track = new TrackDTO();
        track.setId(2);

        //act
        sut.addTrackToPlaylist(playlistId, token, track);

        //assert
        Mockito.verify(mockedPlaylistService).addTrackToPlaylist(playlistId, track);
    }

    @Test
    void addPlaylist() {
    }

    @Test
    void deletePlaylist() {
    }

    @Test
    void addTrackToPlaylist() {
        //arrange
        String token = "1234-1234-1234";
        int playlistId = 3;
        var track = new TrackDTO();
        track.setId(2);

        //act
        sut.addTrackToPlaylist(playlistId, token, track);

        //assert
        Mockito.verify(mockedPlaylistService).addTrackToPlaylist(playlistId, track);
    }

    @Test
    void setPlaylistDAO() {
    }

    @Test
    void setTrackDAO() {
    }
}