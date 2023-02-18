package nl.han.simon.casus.DTOs;

import java.util.ArrayList;

public class Playlist {
    private static int id = 0;

    public Playlist(String name, boolean owner, ArrayList<Track> tracks) {
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
        id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Playlist.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    private String name;
    private boolean owner;
    private ArrayList<Track> tracks = new ArrayList<>();

}
