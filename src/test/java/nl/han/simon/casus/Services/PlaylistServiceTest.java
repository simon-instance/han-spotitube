package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.UserDAO;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.Exceptions.PermissionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {
    private PlaylistService sut;
    private PlaylistService sutSpy;
    private UserDAO mockedUserDAO;
    private PlaylistDAO mockedPlaylistDAO;
    // Testdata
    private String username;
    private PlaylistsWrapperDTO<ConvertedPlaylistDTO> playlists;
    private ConvertedPlaylistDTO convertedPlaylist;

    @BeforeEach
    public void setup() {
        mockedUserDAO = Mockito.mock(UserDAO.class);
        mockedPlaylistDAO = Mockito.mock(PlaylistDAO.class);

        sut = new PlaylistService();

        sut.setPlaylistDAO(mockedPlaylistDAO);
        sut.setUserDAO(mockedUserDAO);

        sutSpy = Mockito.spy(sut);

        // Testdata
        username = "john_doe";
        playlists = new PlaylistsWrapperDTO<>();

        List<ConvertedPlaylistDTO> convertedPlaylists = new ArrayList<>();
        convertedPlaylist = new ConvertedPlaylistDTO();
        convertedPlaylist.setOwner(true);
        convertedPlaylists.add(convertedPlaylist);

        playlists.setPlaylists(convertedPlaylists);
    }

    @Test
    public void getAllPlaylists() {
        // Arrange
        Mockito.doReturn(username).when(mockedUserDAO).getUserNameFromTokenString(ArgumentMatchers.anyString());
        Mockito.doReturn(playlists).when(mockedPlaylistDAO).getPlaylists(ArgumentMatchers.anyString());
        // Act/Assert
        assertEquals(playlists, sutSpy.getAllPlaylists(ArgumentMatchers.anyString()));
    }

    @Test
    public void updatePlaylistName() {
        //arrange
        convertedPlaylist.setName("newName");
        //act
        sutSpy.updatePlaylistName(convertedPlaylist.getId(), convertedPlaylist);
        //assert
        Mockito.verify(sutSpy).updatePlaylistName(convertedPlaylist.getId(), convertedPlaylist);
    }

    @Test
    public void updatePlaylistName_PermissionError() {
        // Arrange
        convertedPlaylist.setOwner(false);
        // Act/Assert
        assertThrows(PermissionException.class, () -> sutSpy.updatePlaylistName(convertedPlaylist.getId(), convertedPlaylist));
        Mockito.verify(sutSpy).updatePlaylistName(convertedPlaylist.getId(), convertedPlaylist);
    }

    @Test
    public void addPlaylist() {
        // Arrange
        Mockito.doReturn(username).when(mockedUserDAO).getUserNameFromTokenString(ArgumentMatchers.anyString());
        // Act
        sutSpy.addPlaylist(convertedPlaylist, username);
        // Assert
        Mockito.verify(sutSpy).addPlaylist(Mockito.isA(ConvertedPlaylistDTO.class), Mockito.anyString());
    }

    @Test
    public void deletePlaylist() {
        // Act
        sutSpy.deletePlaylist(convertedPlaylist.getId());
        // Assert
        Mockito.verify(sutSpy).deletePlaylist(convertedPlaylist.getId());
    }

    @Test
    public void addTrackToPlaylist() {
        // Arrange
        var track = new TrackDTO();
        // Act
        sutSpy.addTrackToPlaylist(convertedPlaylist.getId(), track);
        // Assert
        Mockito.verify(sutSpy).addTrackToPlaylist(ArgumentMatchers.anyInt(), ArgumentMatchers.isA(TrackDTO.class));
    }
}