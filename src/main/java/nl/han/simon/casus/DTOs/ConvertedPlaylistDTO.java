package nl.han.simon.casus.DTOs;

import java.util.List;

public class ConvertedPlaylistDTO extends Playlist {
    public ConvertedPlaylistDTO(int id, String name, boolean owner, List<TrackDTO> tracks) {
        setId(id);
        setName(name);
    }

    public ConvertedPlaylistDTO() {}
    private boolean owner;
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

}
