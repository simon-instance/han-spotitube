package nl.han.simon.casus.DTOs;

public class PlaylistTrackDTO extends TrackDTO {
    private int playlistId;

    public PlaylistTrackDTO() {}

    public PlaylistTrackDTO(int playlistId, int id, String album, String description, int duration, boolean offlineAvailable, String performer, int playcount, String publicationDate, String title) {
        this.setPlaylistId(playlistId);
        this.setId(id);
        this.setAlbum(album);
        this.setDescription(description);
        this.setDuration(duration);
        this.setOfflineAvailable(offlineAvailable);
        this.setPerformer(performer);
        this.setPlaycount(playcount);
        this.setPublicationDate(publicationDate);
        this.setTitle(title);
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getPlaylistId() {
        return this.playlistId;
    }
}
