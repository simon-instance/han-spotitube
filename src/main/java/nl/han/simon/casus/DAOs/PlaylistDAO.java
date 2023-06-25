package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.*;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistDAO {
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    //playlists
    public PlaylistsWrapperDTO<ConvertedPlaylistDTO> getPlaylists(String user) {
        var playlists = database.executeSelectQuery("SELECT id, name, owner FROM playlist p\n", playlistsRowMapper());

        var playlistTracks = getPlaylistTracks();

       List<ConvertedPlaylistDTO> convertedPlaylists = toConvertedPlaylists(playlists, playlistTracks, user);

       var playlistsWrapper = new PlaylistsWrapperDTO<ConvertedPlaylistDTO>();
       playlistsWrapper.setLength(getTotalLength(convertedPlaylists));
       playlistsWrapper.setPlaylists(convertedPlaylists);

       return playlistsWrapper;
    }

    public List<ConvertedPlaylistDTO> toConvertedPlaylists(List<PlaylistDTO> playlists, List<PlaylistTrackDTO> playlistTracks, String user) {
        return playlists.stream().map(playlistDTO -> {
            var tracks = playlistTracks.stream()
                    .filter(p -> p.getPlaylistId() == playlistDTO.getId())
                    .map(this::convertToTrackDTO)
                    .collect(Collectors.toList());

            playlistDTO.setTracks(tracks);
            return toConvertedPlaylistDTO(playlistDTO, user);
        }).collect(Collectors.toList());
    }

    public List<PlaylistTrackDTO> getPlaylistTracks() {
        return database.executeSelectQuery("SELECT pt.playlistId, t.* FROM playlist p\n" +
                "INNER JOIN playlistTracks pt ON p.id = pt.playlistId\n" +
                "INNER JOIN track t ON pt.trackId = t.id", playlistsTracksRowMapper());
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

    public ConvertedPlaylistDTO toConvertedPlaylistDTO(PlaylistDTO playlistDTO, String user) {
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
        return (rs) -> new PlaylistTrackDTO(rs.getInt("playlistId"), rs.getInt("id"), rs.getString("album"), rs.getString("description"), rs.getInt("duration"), rs.getBoolean("offlineAvailable"), rs.getString("performer"), rs.getInt("playcount"), rs.getString("publicationDate"), rs.getString("title"));
    }

    public void updatePlaylistName(int id, String name) {
        database.executeUpdateQuery("""
            UPDATE [playlist]
            SET [name] = ?
            WHERE [id] = ?
        """, name, id);
    }

    public void addTrackToPlaylist(int playlistId, int trackId) {
        database.execute("INSERT INTO [playlistTracks]([playlistId], [trackId]) VALUES(?, ?)", playlistId, trackId);
    }

    public void addPlaylist(String playlistName, String userName) {
        database.execute("INSERT INTO [playlist]([name], [owner]) VALUES(?, ?)", playlistName, userName);
    }

    public void deletePlaylist(int playlistId) {
        database.execute("DELETE FROM [playlist] WHERE [id] = ?", playlistId);
    }
}
