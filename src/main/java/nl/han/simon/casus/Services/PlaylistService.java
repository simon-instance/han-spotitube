package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DTOs.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistService {
    public PlaylistsWrapperDTO getAllPlaylists() throws SQLException {
        var playlists = PlaylistDAO.getPlaylists();

        // Database fetched user based off of tokenString
        var user = "user-1";
        var convertedPlaylists = convertPlaylistOwners(playlists, user);

        var playlistsWrapper = new PlaylistsWrapperDTO();

        playlistsWrapper.setPlaylists(convertedPlaylists);
        playlistsWrapper.setLength(getTotalLength(convertedPlaylists));

        return playlistsWrapper;
    }

    public void updatePlaylistName(int id, String name) throws SQLException {
        PlaylistDAO.updatePlaylistName(id, name);
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
}
