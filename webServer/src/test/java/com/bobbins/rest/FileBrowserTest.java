package com.bobbins.rest;

import com.bobbins.AbstractPlayer;
import com.bobbins.model.FilesystemEntryBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class FileBrowserTest {

    private com.bobbins.Player player;

    @Before
    public void setup(){
        player = new AbstractPlayer(){
            @Override
            public List<FilesystemEntryBean> list(String artist, String album) {
                List<FilesystemEntryBean> list = new ArrayList<>();
                if (artist == null){
                    list.add(new FilesystemEntryBean("artist1", null, null));
                    list.add(new FilesystemEntryBean("artist2", null, null));
                    list.add(new FilesystemEntryBean("artist3", null, null));
                }
                else if (album == null){
                    list.add(new FilesystemEntryBean(artist, "album1", null));
                    list.add(new FilesystemEntryBean(artist, "album2", null));
                    list.add(new FilesystemEntryBean(artist, "album3", null));
                }
                else {
                    list.add(new FilesystemEntryBean(artist, album, "song1"));
                    list.add(new FilesystemEntryBean(artist, album, "song2"));
                    list.add(new FilesystemEntryBean(artist, album, "song3"));
                }
                return list;
            }
        };
    }

    @Test
    public void testRootList(){
        FileBrowser browser = new FileBrowser(player);
        List<FilesystemEntryBean> tracks = browser.rootList();
        assertTrue(!tracks.isEmpty());
        assertEquals("artist1", tracks.get(0).artist);
        assertNull(tracks.get(0).album);
        assertNull(tracks.get(0).song);
    }

    @Test
    public void testListNullArgs(){
        FileBrowser browser = new FileBrowser(player);
        List<FilesystemEntryBean> tracks = browser.list(null, null);
        assertTrue(!tracks.isEmpty());
        assertEquals("artist1", tracks.get(0).artist);
        assertNull(tracks.get(0).album);
        assertNull(tracks.get(0).song);
    }

    @Test
    public void testListOneArg(){
        FileBrowser browser = new FileBrowser(player);
        List<FilesystemEntryBean> tracks = browser.list("foo", null);
        assertTrue(!tracks.isEmpty());
        assertEquals("foo", tracks.get(0).artist);
        assertEquals("album1", tracks.get(0).album);
        assertNull(tracks.get(0).song);
    }

    @Test
    public void testListTwoArgs(){
        FileBrowser browser = new FileBrowser(player);
        List<FilesystemEntryBean> tracks = browser.list("foo", "bar");
        assertTrue(!tracks.isEmpty());
        assertEquals("foo", tracks.get(0).artist);
        assertEquals("bar", tracks.get(0).album);
        assertEquals("song1", tracks.get(0).song);
    }
}
