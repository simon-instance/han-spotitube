package nl.han.simon.casus.DTOs;

public class ConvertedPlaylistDTO extends Playlist {
    private boolean owner;
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

}
