package com.bobbins.rest;

import com.bobbins.PlayerListener;
import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class PlaylistTest {

    private com.bobbins.Player player;

    @Before
    public void setup(){
        player = new com.bobbins.Player(){
            @Override
            public PlaylistBean getPlaylist() {
                List<FilesystemEntryBean> tracks = new ArrayList<>();
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
                return new PlaylistBean(tracks, "song1");
            }

            @Override
            public List<FilesystemEntryBean> list(String artist, String album) {
                return null;
            }

            @Override
            public PlayingStatusBean play(String artist, String album, String song) {
                return null;
            }

            @Override
            public PlayingStatusBean getStatus() {
                return null;
            }

            @Override
            public PlayingStatusBean volume(int volume) {
                return null;
            }

            @Override
            public PlayingStatusBean pause() {
                return null;
            }

            @Override
            public PlayingStatusBean stop() {
                return null;
            }

            @Override
            public void listenForChanges(PlayerListener listener) {

            }

            @Override
            public PlayingStatusBean next() {
                return null;
            }

            @Override
            public PlayingStatusBean previous() {
                return null;
            }

            @Override
            public PlayingStatusBean play() {
                return null;
            }

            @Override
            public PlayingStatusBean seek(int positionInSeconds) {
                return null;
            }

        };
    }

    @Test
    public void testGetPlaylist(){
        Playlist playlistService = new Playlist(player);
        PlaylistBean listBean = playlistService.playlist();
        assertTrue(!listBean.trackList.isEmpty());
        assertEquals(listBean.trackList.get(listBean.currentTrackNumber).name, listBean.currentTrackName);
    }

}
