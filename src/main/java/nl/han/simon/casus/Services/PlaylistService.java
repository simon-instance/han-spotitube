package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DAOs.UserDAO;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Exceptions.PermissionException;


public class PlaylistService {
    public PlaylistDAO playlistDAO;
    public TrackDAO trackDAO;
    public UserDAO userDAO;

    public PlaylistsWrapperDTO getAllPlaylists(String tokenString) {
        var user = userDAO.getUserNameFromTokenString(tokenString);
        var playlists = playlistDAO.getPlaylists(user);

        return playlists;
    }

    public void updatePlaylistName(int id, ConvertedPlaylistDTO newPlaylist) {
        if(!newPlaylist.isOwner()) {
            throw new PermissionException("You're not the owner of this playlist");
        }
        playlistDAO.updatePlaylistName(id, newPlaylist.getName());
    }

    public void addPlaylist(ConvertedPlaylistDTO newPlaylist, String tokenString) {
        var user = userDAO.getUserNameFromTokenString(tokenString);
        playlistDAO.addPlaylist(newPlaylist.getName(), user);
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

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
