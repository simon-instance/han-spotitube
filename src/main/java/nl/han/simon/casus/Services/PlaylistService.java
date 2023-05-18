package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Exceptions.DBException;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistService {
    public PlaylistDAO playlistDAO;
    public TrackDAO trackDAO;

    public PlaylistsWrapperDTO getAllPlaylists() {
        String user = "jane_doe";
        var playlists = playlistDAO.getPlaylists(user);

        return playlists;
    }

    public void updatePlaylistName(int id, String name) {
        playlistDAO.updatePlaylistName(id, name);
    }

    public void addPlaylist(ConvertedPlaylistDTO newPlaylist, String tokenString) {
        playlistDAO.addPlaylist(newPlaylist.getName(), tokenString);
    }

    public void deletePlaylist(int playlistId) {
        playlistDAO.deletePlaylist(playlistId);
    }

    public void addTrackToPlaylist(int playlistId, TrackDTO trackDTO) {
        playlistDAO.addTrackToPlaylist(playlistId, trackDTO.getId());
    }


    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
