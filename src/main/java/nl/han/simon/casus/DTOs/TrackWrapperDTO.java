package nl.han.simon.casus.DTOs;

import java.util.List;

public class TrackWrapperDTO {
    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    private List<TrackDTO> tracks;
}
