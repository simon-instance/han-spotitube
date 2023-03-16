package nl.han.simon.casus.Services;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import nl.han.simon.casus.Exceptions.DBException;

import java.sql.SQLException;

public class TrackService {
    private TrackDAO trackDAO;

    public Response getTracksByPlaylist(int playlistId) {
        try {
            var tracks = trackDAO.getPlaylistTracks(playlistId);

            var wrappedTracks = new TrackWrapperDTO();
            wrappedTracks.setTracks(tracks);

            return Response.ok().entity(wrappedTracks).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Response getTracksExcludePlaylist(int playlistId) {
        try {
            var tracks = trackDAO.getAllTracksExcept(playlistId);

            return Response.ok().entity(tracks).build();
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
