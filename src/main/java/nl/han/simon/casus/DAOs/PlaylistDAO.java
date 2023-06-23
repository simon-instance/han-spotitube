package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.*;
import nl.han.simon.casus.Exceptions.DBException;
import nl.han.simon.casus.Exceptions.InsertException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlaylistDAO {
    private Database database;
    @Inject
    public void setDatabase(Database database) { this.database = database; }

    private TrackDAO trackDAO;

    //playlists
    public PlaylistsWrapperDTO<ConvertedPlaylistDTO> getPlaylists(String user) {
        try {
            var playlists = database.executeSelectQuery("SELECT id, name, owner FROM playlist p\n", playlistsRowMapper());

            var playlistTracks = database.executeSelectQuery("SELECT pt.playlistId, t.* FROM playlist p\n" +
                    "INNER JOIN playlistTracks pt ON p.id = pt.playlistId\n" +
                    "INNER JOIN track t ON pt.trackId = t.id", playlistsTracksRowMapper());

           List<ConvertedPlaylistDTO> convertedPlaylists = playlists.stream().map(playlistDTO -> {
                var tracks = playlistTracks.stream()
                        .filter(p -> p.getPlaylistId() == playlistDTO.getId())
                        .map(this::convertToTrackDTO)
                        .collect(Collectors.toList());

                playlistDTO.setTracks(tracks);
                return toConvertedPlaylistDTO(playlistDTO, user);
            }).collect(Collectors.toList());

           var playlistsWrapper = new PlaylistsWrapperDTO<ConvertedPlaylistDTO>();
           playlistsWrapper.setLength(getTotalLength(convertedPlaylists));
           playlistsWrapper.setPlaylists(convertedPlaylists);

           return playlistsWrapper;
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

    public TrackDTO convertToTrackDTO(PlaylistTrackDTO playlistTrackDTO) {
        TrackDTO convertedTrack = new TrackDTO();
        convertedTrack.setId(playlistTrackDTO.getId());
        convertedTrack.setTitle(playlistTrackDTO.getTitle());
        convertedTrack.setPerformer(playlistTrackDTO.getPerformer());
        convertedTrack.setDuration(playlistTrackDTO.getDuration());
        convertedTrack.setAlbum(playlistTrackDTO.getAlbum());
        convertedTrack.setPlaycount(playlistTrackDTO.getPlaycount());
        convertedTrack.setPublicationDate(playlistTrackDTO.getPublicationDate());
        convertedTrack.setDescription(playlistTrackDTO.getDescription());
        convertedTrack.setOfflineAvailable(playlistTrackDTO.isOfflineAvailable());
        return convertedTrack;
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
        convertedPlaylistDTO.setOwner(playlistDTO.getOwner().equals(user));
        convertedPlaylistDTO.setName(playlistDTO.getName());

        return convertedPlaylistDTO;
    }

    public RowMapper<PlaylistDTO> playlistsRowMapper() {
        return (rs) -> new PlaylistDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
    }

    public RowMapper<PlaylistTrackDTO> playlistsTracksRowMapper() {
        return (rs) -> {
            int playlistId = rs.getInt("playlistId");
            int id = rs.getInt("id");
            String album = rs.getString("album");
            String description = rs.getString("description");
            int duration = rs.getInt("duration");
            boolean offlineAvailable = rs.getBoolean("offlineAvailable");
            String performer = rs.getString("performer");
            int playcount = rs.getInt("playcount");
            String publicationDate = rs.getString("publicationDate");
            String title = rs.getString("title");

            return new PlaylistTrackDTO(playlistId, id, album, description, duration, offlineAvailable, performer, playcount, publicationDate, title);
        };
    }
    // end playlists

    public void updatePlaylistName(int id, String name) {
        try {
            database.executeUpdateQuery("""
                UPDATE [playlist]
                SET [name] = ?
                WHERE [id] = ?
            """, name, id);
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void addTrackToPlaylist(int playlistId, int trackId) {
//        try {
//            database.execute("INSERT INTO [playlistTracks]([playlistId], [trackId]) VALUES(?, ?)", playlistId, trackId);
//        } catch(SQLException e) {
//            throw new DBException(e.getMessage());
//        }
    }

    public void addPlaylist(String playlistName, String userName) {
        try {
            database.execute("INSERT INTO [playlist]([name], [owner]) VALUES(?, ?)", playlistName, userName);
        } catch (SQLException e) {
            throw new InsertException("Failed to create playlist " + playlistName);
        }
    }

    public void deletePlaylist(int playlistId) {
        try {
            database.execute("DELETE FROM [playlist] WHERE [id] = ?", playlistId);
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
