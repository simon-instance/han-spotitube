package nl.han.simon.casus.DTOs;

import java.util.List;

public class PlaylistsWrapperDTO {
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<ConvertedPlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<ConvertedPlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    private int length;
    private List<ConvertedPlaylistDTO> playlists;


}
