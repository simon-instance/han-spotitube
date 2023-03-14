package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TrackDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    public static List<TrackDTO> getPlaylistTracks(int playlistId) throws SQLException {
        List<TrackDTO> tracks = new ArrayList<>();

        var trackResult = Database.executeSelectQuery("SELECT * from track\n" +
                "WHERE id IN\n" +
                "(SELECT trackId FROM playlistTracks WHERE playlistId = ?)", playlistId);

        while (trackResult.next()) {
            TrackDTO track = new TrackDTO();
            track.setId(trackResult.getInt("id"));
            track.setTitle(trackResult.getString("title"));
            track.setPerformer(trackResult.getString("performer"));
            track.setDuration(trackResult.getInt("duration"));
            track.setAlbum(trackResult.getString("album"));
            track.setPlaycount(trackResult.getInt("playcount"));
            track.setPublicationDate(trackResult.getDate("publicationDate"));
            track.setDescription(trackResult.getString("description"));
            track.setOfflineAvailable(trackResult.getBoolean("offlineAvailable"));
            tracks.add(track);
        }

        return tracks;
    }
}
