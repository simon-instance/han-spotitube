package nl.han.simon.casus.DAOs;

import nl.han.simon.casus.DTOs.TrackDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrackDAO {
    public static List<TrackDTO> getPlaylistTracks(int playlistId) {
        var tracks = IntStream.range(1, 3).mapToObj(k -> {
            var track = new TrackDTO();

            track.setAlbum("album-" + k);
            track.setDescription("description-" + k);
            track.setTitle("title-" + k);
            track.setDuration(100);
            track.setId(k);
            track.setPerformer("performer-" + k);
            track.setPlaycount(0);
            track.setOfflineAvailable(false);

            return track;
        }).collect(Collectors.toList());

        return tracks;
    }
}
