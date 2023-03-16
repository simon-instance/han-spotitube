package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    public List<TrackDTO> getPlaylistTracks(int playlistId) throws SQLException {
        List<TrackDTO> tracks = new ArrayList<>();

        var trackResult = Database.executeSelectQuery("SELECT * from track\n" +
                "WHERE id IN\n" +
                "(SELECT trackId FROM playlistTracks WHERE playlistId = ?)", playlistId);

        retrieveTrackData(trackResult, tracks);

        return tracks;
    }

    public TrackWrapperDTO getAllTracksExcept(int playlistId) throws SQLException {
        var trackWrapperDTO = new TrackWrapperDTO();

        var trackResult = Database.executeSelectQuery("SELECT * FROM [track] WHERE [id] NOT IN (SELECT [trackId] FROM [playlistTracks] WHERE [playlistId] = ?)", playlistId);

        List<TrackDTO> tracks = new ArrayList<>();
        retrieveTrackData(trackResult, tracks);

        trackWrapperDTO.setTracks(tracks);

        return trackWrapperDTO;
    }

    private void retrieveTrackData(ResultSet trackResult, List<TrackDTO> tracks) throws SQLException {
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
    }
}
