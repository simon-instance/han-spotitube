package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.PlaylistDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    public static List<PlaylistDTO> getPlaylists() throws SQLException {
        var result = Database.executeSelectQuery("SELECT * FROM playlist");

        List<PlaylistDTO> playlists = new ArrayList<>();
        while (result.next()) {
            PlaylistDTO playlist = new PlaylistDTO();
            playlist.setId(result.getInt("id"));
            playlist.setName(result.getString("name"));
            playlist.setOwner(result.getString("owner"));

            playlist.setTracks(TrackDAO.getPlaylistTracks(playlist.getId()));

            playlists.add(playlist);
        }

        return playlists;
    }

    public static void updatePlaylistName(int id, String name) throws SQLException {
        Database.executeUpdateQuery("UPDATE [playlist] \n" +
                "SET [name] = ?\n" +
                "WHERE [id] = ?", name, id);
    }
}
