package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private TrackDAO trackDAO;

    public List<PlaylistDTO> getPlaylists(String user) throws SQLException {
        var result = Database.executeSelectQuery("SELECT * FROM playlist WHERE owner = ?", user);

        List<PlaylistDTO> playlists = new ArrayList<>();
        while (result.next()) {
            PlaylistDTO playlist = new PlaylistDTO();
            playlist.setId(result.getInt("id"));
            playlist.setName(result.getString("name"));
            playlist.setOwner(user);

            playlist.setTracks(trackDAO.getPlaylistTracks(playlist.getId()));

            playlists.add(playlist);
        }

        return playlists;
    }

    public void updatePlaylistName(int id, String name) throws SQLException {
        Database.executeUpdateQuery("UPDATE [playlist] \n" +
                "SET [name] = ?\n" +
                "WHERE [id] = ?", name, id);
    }

    public void addTrackToPlaylist(int playlistId, int trackId) throws SQLException {
        Database.execute("INSERT INTO [playlistTracks]([playlistId], [trackId]) VALUES(?, ?)", playlistId, trackId);
    }

    public static void addPlaylist(String playlistName, String tokenString) throws SQLException {
        Database.execute("INSERT INTO [playlist]([name], [owner]) VALUES(?, ?)", playlistName, tokenString);
    }

    public static void deletePlaylist(int playlistId) throws SQLException {
        Database.execute("DELETE FROM [playlist] WHERE [id] = ?", playlistId);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
