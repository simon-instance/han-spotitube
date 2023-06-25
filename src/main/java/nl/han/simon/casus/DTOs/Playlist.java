package nl.han.simon.casus.DTOs;

import java.util.List;

public abstract class Playlist {
    private int id;
    private List<? extends TrackDTO> tracks;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<? extends TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<? extends TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
