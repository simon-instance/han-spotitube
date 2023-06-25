package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;

public class TrackService {
    private TrackDAO trackDAO;

    public TrackWrapperDTO getTracksByPlaylist(int playlistId) {
        var tracks = trackDAO.getPlaylistTracks(playlistId);

        var wrappedTracks = new TrackWrapperDTO();
        wrappedTracks.setTracks(tracks);

        return wrappedTracks;
    }

    public TrackWrapperDTO getTracksExcludePlaylist(int playlistId) {
        TrackWrapperDTO tracks;
        if(playlistId == 0) {
            tracks = trackDAO.getAllTracks();
        } else {
            tracks = trackDAO.getAllTracksExcept(playlistId);
        }

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
