package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.PlaylistDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlaylistDAO {
    public static List<PlaylistDTO> getPlaylists() throws SQLException {
        var result = Database.executeSelectQuery("SELECT * FROM playlist");



        var dtos = IntStream.range(1, 10).mapToObj(i -> {
            var playlist = new PlaylistDTO();
            playlist.setId(i);
            playlist.setOwner("user-" + i);

            var tracks = TrackDAO.getPlaylistTracks(i);
            playlist.setTracks(tracks);

            return playlist;
        }).collect(Collectors.toList());

        return dtos;
    }
}
