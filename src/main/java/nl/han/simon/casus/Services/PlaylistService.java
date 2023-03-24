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
        String user = "john_doe";
        var playlists = playlistDAO.getPlaylists(user);

        // Database fetched user based off of tokenString
        var convertedPlaylists = convertPlaylistOwners(playlists, user);

        var playlistsWrapper = new PlaylistsWrapperDTO();

        playlistsWrapper.setPlaylists(convertedPlaylists);
        playlistsWrapper.setLength(getTotalLength(convertedPlaylists));

        return playlistsWrapper;
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

    private int getTotalLength(List<? extends Playlist> playlists) {
        return playlists
                .stream()
                .mapToInt(this::getPlaylistLength)
                .sum();
    }

    private int getPlaylistLength(Playlist playlistDTO) {
        return playlistDTO
                .getTracks()
                .stream()
                .mapToInt(TrackDTO::getDuration).sum();
    }

    private List<ConvertedPlaylistDTO> convertPlaylistOwners(List<PlaylistDTO> playlists, String user) {
        return playlists
                .stream()
                .map(p -> toConvertedPlaylistDTO(p, user))
                .collect(Collectors.toList());
    }

    private ConvertedPlaylistDTO toConvertedPlaylistDTO(PlaylistDTO playlistDTO, String user) {
        ConvertedPlaylistDTO convertedPlaylistDTO = new ConvertedPlaylistDTO();

        convertedPlaylistDTO.setId(playlistDTO.getId());
        convertedPlaylistDTO.setTracks(playlistDTO.getTracks());
        convertedPlaylistDTO.setOwner(playlistDTO.getOwner() == user);
        convertedPlaylistDTO.setName(playlistDTO.getName());

        return convertedPlaylistDTO;
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
