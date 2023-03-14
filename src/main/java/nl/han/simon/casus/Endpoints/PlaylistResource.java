package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlists(@QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        return playlistService.getAllPlaylists();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlistTracks(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        return trackService.getTracksByPlaylist(playlistId);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylistName(@PathParam("id") int playlistId, @QueryParam("token") String tokenString, PlaylistDTO newPlaylist) {
        return playlistService.updatePlaylistName(playlistId, newPlaylist.getName(), tokenString);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String tokenString, ConvertedPlaylistDTO newPlaylist) {
        return playlistService.addPlaylist(newPlaylist, tokenString);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        return playlistService.deletePlaylist(playlistId, tokenString);
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
