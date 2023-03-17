package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;

@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;
    @GET
    public Response playlists(@QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        return playlistService.getAllPlaylists();
    }

    @GET
    @Path("/{id}/tracks")
    public Response playlistTracks(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        return trackService.getTracksByPlaylist(playlistId);
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenString, TrackDTO track) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        return playlistService.addTrackToPlaylist(playlistId, tokenString, track);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlaylistName(ConvertedPlaylistDTO newPlaylist, @PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        return playlistService.updatePlaylistName(playlistId, newPlaylist.getName(), tokenString);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String tokenString, ConvertedPlaylistDTO newPlaylist) {
        return playlistService.addPlaylist(newPlaylist, tokenString);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        return playlistService.deletePlaylist(playlistId, tokenString);
    }

    @DELETE
    @Path("/{id}/tracks/{trackid}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String tokenString) {
        return trackService.deleteTrackFromPlaylist(trackId, playlistId, tokenString);
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
