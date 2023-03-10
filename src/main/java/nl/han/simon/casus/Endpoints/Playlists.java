package nl.han.simon.casus.Endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import nl.han.simon.casus.Services.PlaylistService;

//@Path("/playlists")
public class Playlists {
    @Inject
    public PlaylistService playlistService;
}
