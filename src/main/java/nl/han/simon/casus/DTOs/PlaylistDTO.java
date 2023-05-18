package nl.han.simon.casus.DTOs;

public class PlaylistDTO extends Playlist {
    public PlaylistDTO(int id, String name, String owner) {
        this.setId(id);
        this.setName(name);
        this.setOwner(owner);
    }
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
