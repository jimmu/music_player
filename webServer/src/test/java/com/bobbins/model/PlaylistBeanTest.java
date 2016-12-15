package com.bobbins.model;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlaylistBeanTest {

    @Before
    public void setup(){

    }

    @Test
    public void testConstructor(){
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");
        assertTrue(playlist.trackCount == 3);
        assertEquals(playlist.currentTrackName, "song2");
        assertTrue(playlist.currentTrackNumber == 1); //zero-indexed
    }

    @Test
    public void testEquals() {
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");

        List<FilesystemEntryBean> similarTracks = new ArrayList<>();
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean similarPlaylist = new PlaylistBean(similarTracks, "song2");

        assertEquals(playlist, similarPlaylist);
    }


    @Test
    public void testNotEqualsBecauseDifferentPlaylists() {
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");

        List<FilesystemEntryBean> dissimilarTracks = new ArrayList<>();
        dissimilarTracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        dissimilarTracks.add(new FilesystemEntryBean("artist1", "album2", "song2"));
        dissimilarTracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean similarPlaylist = new PlaylistBean(dissimilarTracks, "song2");

        assertFalse(playlist.equals(similarPlaylist));
    }

    @Test
    public void testNotEqualsBecauseDifferentCurrentTrackNumber() {
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");

        List<FilesystemEntryBean> similarTracks = new ArrayList<>();
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean similarPlaylist = new PlaylistBean(similarTracks, "song3");

        assertFalse(playlist.equals(similarPlaylist));
    }

    @Test
    public void testHashCodeEqualObjects() {
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");

        List<FilesystemEntryBean> similarTracks = new ArrayList<>();
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean similarPlaylist = new PlaylistBean(similarTracks, "song2");

        assertTrue(playlist.hashCode() == similarPlaylist.hashCode());
    }

    @Test
    public void testHashCodeUnequalObjects() {
        List<FilesystemEntryBean> tracks = new ArrayList<>();
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
        PlaylistBean playlist = new PlaylistBean(tracks, "song2");

        List<FilesystemEntryBean> similarTracks = new ArrayList<>();
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
        similarTracks.add(new FilesystemEntryBean("artist1", "album2", "song3"));
        PlaylistBean similarPlaylist = new PlaylistBean(similarTracks, "song2");

        assertFalse(playlist.hashCode() == similarPlaylist.hashCode());
    }
}
