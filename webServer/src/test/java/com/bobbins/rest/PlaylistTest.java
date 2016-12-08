package com.bobbins.rest;

import com.bobbins.AbstractPlayer;
import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlaylistBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlaylistTest {

    private com.bobbins.Player player;

    @Before
    public void setup(){
        player = new AbstractPlayer(){
            @Override
            public PlaylistBean getPlaylist() {
                List<FilesystemEntryBean> tracks = new ArrayList<>();
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song1"));
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song2"));
                tracks.add(new FilesystemEntryBean("artist1", "album1", "song3"));
                return new PlaylistBean(tracks, "song1");
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
