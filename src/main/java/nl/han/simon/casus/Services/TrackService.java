package nl.han.simon.casus.Services;

import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;

public class TrackService {
    public TrackWrapperDTO getTracksByPlaylist(int playlistId) {
        var tracks = TrackDAO.getPlaylistTracks(playlistId);

        var wrappedTracks = new TrackWrapperDTO();
        wrappedTracks.setTracks(tracks);

        return wrappedTracks;
    }
}
