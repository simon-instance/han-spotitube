package nl.han.simon.casus.DAOs;

import jakarta.inject.Inject;
import nl.han.simon.casus.DB.Database;
import nl.han.simon.casus.DB.RowMapper;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import java.util.List;

public class TrackDAO {
    private Database database;

    @Inject
    public void setDatabase(Database database) { this.database = database; }

    public List<TrackDTO> getPlaylistTracks(int playlistId) {
        var trackResult = database.executeSelectQuery("SELECT * FROM track\n" +
                "WHERE id IN\n" +
                "(SELECT trackId FROM playlistTracks WHERE playlistId = ?)", getTrackMapper(), playlistId);

        return trackResult;
    }

    public TrackWrapperDTO getAllTracksExcept(int playlistId) {
        var trackWrapperDTO = new TrackWrapperDTO();

        var trackResult = database.executeSelectQuery("SELECT * FROM [track] WHERE [id] NOT IN (SELECT [trackId] FROM [playlistTracks] WHERE [playlistId] = ?)", getTrackMapper(), playlistId);

        trackWrapperDTO.setTracks(trackResult);

        return trackWrapperDTO;
    }

    public TrackWrapperDTO getAllTracks() {
        var trackWrapperDTO = new TrackWrapperDTO();

        var trackResult = database.executeSelectQuery("SELECT * FROM [track]", getTrackMapper());

        trackWrapperDTO.setTracks(trackResult);

        return trackWrapperDTO;
    }

    public void deleteTrackFromPlaylist(int trackId, int playlistId) {
        database.execute("DELETE FROM [playlistTracks] WHERE [trackId] = ? AND [playlistId] = ?", trackId, playlistId);
    }

    public RowMapper<TrackDTO> getTrackMapper() {
        return (res) -> new TrackDTO(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), res.getInt(6), res.getString(7), res.getString(8), res.getBoolean(9));
    }
}
