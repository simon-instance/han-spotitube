package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.Services.TrackService;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String tokenString) {
        var tracks = trackService.getTracksExcludePlaylist(playlistId);
        return Response.ok().entity(tracks).build();
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
