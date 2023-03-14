package nl.han.simon.casus.Services;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Exceptions.SQLExceptionMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistService {
    public Response getAllPlaylists() {
        try {
            var playlists = PlaylistDAO.getPlaylists();

            // Database fetched user based off of tokenString
            var user = "user-1";
            var convertedPlaylists = convertPlaylistOwners(playlists, user);

            var playlistsWrapper = new PlaylistsWrapperDTO();

            playlistsWrapper.setPlaylists(convertedPlaylists);
            playlistsWrapper.setLength(getTotalLength(convertedPlaylists));

            return Response.ok().entity(playlistsWrapper).build();
        } catch(SQLException e) {
            return new SQLExceptionMapper().toResponse(e);
        }
    }

    public Response updatePlaylistName(int id, String name, String tokenString) {
        try {
            PlaylistDAO.updatePlaylistName(id, name);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch (SQLException e) {
            return new SQLExceptionMapper().toResponse(e);
        }
    }

    public Response addPlaylist(ConvertedPlaylistDTO newPlaylist, String tokenString) {
        try {
            PlaylistDAO.addPlaylist(newPlaylist.getName(), tokenString);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            return new SQLExceptionMapper().toResponse(e);
        }
    }

    public Response deletePlaylist(int playlistId, String tokenString) {
        try {
            PlaylistDAO.deletePlaylist(playlistId);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            return new SQLExceptionMapper().toResponse(e);
        }
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
