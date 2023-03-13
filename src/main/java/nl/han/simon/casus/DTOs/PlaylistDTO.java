package nl.han.simon.casus.DTOs;

public class PlaylistDTO extends Playlist {
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
