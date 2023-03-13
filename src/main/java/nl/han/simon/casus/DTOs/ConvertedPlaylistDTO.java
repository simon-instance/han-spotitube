package nl.han.simon.casus.DTOs;

public class ConvertedPlaylistDTO extends Playlist {
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    private boolean owner;
}
