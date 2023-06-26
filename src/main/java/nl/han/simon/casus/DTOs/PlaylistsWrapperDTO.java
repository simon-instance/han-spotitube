package nl.han.simon.casus.DTOs;

import java.util.List;

public class PlaylistsWrapperDTO<T extends Playlist> {
    public PlaylistsWrapperDTO() {
    }
    public PlaylistsWrapperDTO(List<T> playlists) {
        this.playlists = playlists;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<T> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<T> playlists) {
        this.playlists = playlists;
    }

    private int length;
    private List<T> playlists;


}
