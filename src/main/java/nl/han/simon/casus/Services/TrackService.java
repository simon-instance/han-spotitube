package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;

import java.sql.SQLException;

public class TrackService {
    public TrackWrapperDTO getTracksByPlaylist(int playlistId) throws SQLException {
        var tracks = TrackDAO.getPlaylistTracks(playlistId);

        var wrappedTracks = new TrackWrapperDTO();
        wrappedTracks.setTracks(tracks);

        return wrappedTracks;
    }
}
