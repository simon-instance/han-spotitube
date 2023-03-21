package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;

import java.net.URI;
import java.sql.SQLException;

public class TrackService {
    private TrackDAO trackDAO;

    public TrackWrapperDTO getTracksByPlaylist(int playlistId) {
        var tracks = trackDAO.getPlaylistTracks(playlistId);

        var wrappedTracks = new TrackWrapperDTO();
        wrappedTracks.setTracks(tracks);

        return wrappedTracks;
    }

    public TrackWrapperDTO getTracksExcludePlaylist(int playlistId) {
        var tracks = trackDAO.getAllTracksExcept(playlistId);

        return tracks;
    }

    public void deleteTrackFromPlaylist(int trackId, int playlistId, String tokenString) {
        trackDAO.deleteTrackFromPlaylist(trackId, playlistId);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
