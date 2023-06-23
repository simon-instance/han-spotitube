package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import nl.han.simon.casus.DTOs.ConvertedPlaylistDTO;
import nl.han.simon.casus.DTOs.TrackDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;

import java.net.URI;

@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;
    @GET
    public Response playlists(@QueryParam("token") String tokenString) {
        var playlistsWrapper = playlistService.getAllPlaylists(tokenString);
        return Response.ok().entity(playlistsWrapper).build();
    }

    @GET
    @Path("/{id}/tracks")
    public Response playlistTracks(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        var wrappedTracks = trackService.getTracksByPlaylist(playlistId);
        return Response.ok().entity(wrappedTracks).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenString, TrackDTO track) {
        playlistService.addTrackToPlaylist(playlistId, track);

        URI uri = UriBuilder.fromPath("/playlists/" + playlistId + "/tracks").queryParam("token", tokenString).build();
        return Response.status(303).location(uri).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlaylistName(ConvertedPlaylistDTO newPlaylist, @PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        playlistService.updatePlaylistName(playlistId, newPlaylist);

        URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
        return Response.status(303).location(uri).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(ConvertedPlaylistDTO newPlaylist, @QueryParam("token") String tokenString) {
        playlistService.addPlaylist(newPlaylist, tokenString);

        URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
        return Response.status(303).location(uri).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        playlistService.deletePlaylist(playlistId);

        URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
        return Response.status(303).location(uri).build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackid}")
    public Response deleteTrackFromPlaylist(@PathParam("id") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String tokenString) {
        trackService.deleteTrackFromPlaylist(trackId, playlistId, tokenString);

        URI uri = UriBuilder.fromPath("/playlists/" + playlistId + "/tracks").queryParam("token", tokenString).build();
        return Response.status(303).location(uri).build();
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
