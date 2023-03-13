package nl.han.simon.casus.DTOs;

import java.util.List;

public abstract class Playlist {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    private int id;
    private List<TrackDTO> tracks;
}
