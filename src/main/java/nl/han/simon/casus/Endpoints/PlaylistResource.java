package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import nl.han.simon.casus.DTOs.Playlist;
import nl.han.simon.casus.DTOs.PlaylistDTO;
import nl.han.simon.casus.DTOs.PlaylistsWrapperDTO;
import nl.han.simon.casus.DTOs.TrackWrapperDTO;
import nl.han.simon.casus.Services.PlaylistService;
import nl.han.simon.casus.Services.TrackService;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.sql.SQLException;

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

        try {
            PlaylistsWrapperDTO allPlaylists = playlistService.getAllPlaylists();
            return Response.ok().entity(allPlaylists).build();
        } catch(SQLException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlistTracks(@PathParam("id") int playlistId, @QueryParam("token") String tokenString) {
        if(!"1234-1234-1234".equals(tokenString)) {
            return Response.status(403).build();
        }

        try {
            var playlistTracks = trackService.getTracksByPlaylist(playlistId);
            return Response.ok().entity(playlistTracks).build();
        } catch(SQLException e) {
            return Response.status(500).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylistName(@PathParam("id") int playlistId, @QueryParam("token") String tokenString, PlaylistDTO newPlaylist) {
//        return Response.ok().entity("{ \"bami\": true }").build();
        try {
            playlistService.updatePlaylistName(playlistId, newPlaylist.getName());

            URI uri = UriBuilder.fromPath("/playlists").queryParam("token", tokenString).build();
            return Response.status(303).location(uri).build();
        } catch(SQLException e) {
            System.out.println(e);
            return Response.status(500).build();
        }
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
