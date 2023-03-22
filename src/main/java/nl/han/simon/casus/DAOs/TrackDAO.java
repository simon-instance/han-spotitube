package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    public List<TrackDTO> getPlaylistTracks(int playlistId) {
        try {
            List<TrackDTO> tracks = new ArrayList<>();

            var trackResult = Database.executeSelectQuery("SELECT * FROM track\n" +
                    "WHERE id IN\n" +
                    "(SELECT trackId FROM playlistTracks WHERE playlistId = ?)", playlistId);

            retrieveTrackData(trackResult, tracks);

            return tracks;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public TrackWrapperDTO getAllTracksExcept(int playlistId) {
        try {
            var trackWrapperDTO = new TrackWrapperDTO();

            var trackResult = Database.executeSelectQuery("SELECT * FROM [track] WHERE [id] NOT IN (SELECT [trackId] FROM [playlistTracks] WHERE [playlistId] = ?)", playlistId);

            List<TrackDTO> tracks = new ArrayList<>();
            retrieveTrackData(trackResult, tracks);

            trackWrapperDTO.setTracks(tracks);

            return trackWrapperDTO;
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void deleteTrackFromPlaylist(int trackId, int playlistId) {
        try {
            Database.execute("DELETE FROM [playlistTracks] WHERE [trackId] = ? AND [playlistId] = ?", trackId, playlistId);
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
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
            track.setPublicationDate(trackResult.getString("publicationDate"));
            track.setDescription(trackResult.getString("description"));
            track.setOfflineAvailable(trackResult.getBoolean("offlineAvailable"));
            tracks.add(track);
        }
    }
}
