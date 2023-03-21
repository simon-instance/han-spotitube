package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.Exceptions.DBException;
import nl.han.simon.casus.Exceptions.InsertException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private TrackDAO trackDAO;

    public List<PlaylistDTO> getPlaylists(String user) {
        try {
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
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void updatePlaylistName(int id, String name) throws SQLException {
        Database.executeUpdateQuery("UPDATE [playlist] \n" +
                "SET [name] = ?\n" +
                "WHERE [id] = ?", name, id);
    }

    public void addTrackToPlaylist(int playlistId, int trackId) throws SQLException {
        Database.execute("INSERT INTO [playlistTracks]([playlistId], [trackId]) VALUES(?, ?)", playlistId, trackId);
    }

    public void addPlaylist(String playlistName, String tokenString) throws SQLException {
        var user = Database.executeSelectQuery("SELECT * FROM [user] WHERE token = ?", tokenString);

        if(user.next()) {
            Database.execute("INSERT INTO [playlist]([name], [owner]) VALUES(?, ?)", playlistName, user.getString("user"));
            return;
        }

        throw new InsertException("Failed to create playlist " + playlistName);
    }

    public static void deletePlaylist(int playlistId) throws SQLException {
        Database.execute("DELETE FROM [playlist] WHERE [id] = ?", playlistId);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
