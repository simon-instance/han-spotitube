package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import nl.han.simon.casus.DAOs.PlaylistDAO;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Exceptions.DBException;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistService {
    public PlaylistDAO playlistDAO;
    public TrackDAO trackDAO;

    public Response getAllPlaylists() {
        try {
            String user = "john_doe";
            var playlists = playlistDAO.getPlaylists(user);

            // Database fetched user based off of tokenString
            var convertedPlaylists = convertPlaylistOwners(playlists, user);

            var playlistsWrapper = new PlaylistsWrapperDTO();

            playlistsWrapper.setPlaylists(convertedPlaylists);
            playlistsWrapper.setLength(getTotalLength(convertedPlaylists));

            return Response.ok().entity(playlistsWrapper).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Response updatePlaylistName(int id, String name, String tokenString) {
        try {
            playlistDAO.updatePlaylistName(id, name);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Response addPlaylist(ConvertedPlaylistDTO newPlaylist, String tokenString) {
        try {
            playlistDAO.addPlaylist(newPlaylist.getName(), tokenString);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Response deletePlaylist(int playlistId, String tokenString) {
        try {
            playlistDAO.deletePlaylist(playlistId);

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Response addTrackToPlaylist(int playlistId, String tokenString, TrackDTO trackDTO) {
        try {
            playlistDAO.addTrackToPlaylist(playlistId, trackDTO.getId());

            URI uri = UriBuilder.fromPath("/playlists/" + playlistId + "/tracks").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
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

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
