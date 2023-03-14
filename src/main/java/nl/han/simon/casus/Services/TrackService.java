package nl.han.simon.casus.Services;

import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DAOs.TrackDAO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import nl.han.simon.casus.Exceptions.SQLExceptionMapper;

import java.sql.SQLException;

public class TrackService {
    public Response getTracksByPlaylist(int playlistId) {
        try {
            var tracks = TrackDAO.getPlaylistTracks(playlistId);

            var wrappedTracks = new TrackWrapperDTO();
            wrappedTracks.setTracks(tracks);

            return Response.ok().entity(wrappedTracks).build();
        } catch(SQLException e) {
            return new SQLExceptionMapper().toResponse(e);
        }
    }
}
