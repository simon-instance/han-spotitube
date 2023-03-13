package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.DTOs.Playlist;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;

    @GET
    public Response playlists(@QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }
        PlaylistsWrapperDTO allPlaylists = playlistService.getAllPlaylists(tokenString);

        return Response.ok().entity(allPlaylists).build();
    }

    @GET
    @Path("/{id}/tracks")
    public Response playlistTracks(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        var playlistTracks = trackService.getTracksByPlaylist(playlistId);
        return Response.ok().entity(playlistTracks).build();
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
