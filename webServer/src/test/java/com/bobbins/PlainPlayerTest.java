package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class PlainPlayerTest {

    private PlainPlayer player;

    @Before
    public void setup() {
        player = new PlainPlayer();
    }

    @Test
    public void testListRoot() throws PlayerException {
        List<FilesystemEntryBean> list = player.list(null, null);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        for (FilesystemEntryBean artist : list){
            assertTrue(artist.isArtist);
            assertFalse(artist.isAlbum);
            assertFalse(artist.isSong);
        }
    }

    @Test
    public void testListArtist() throws PlayerException {
        // Need to know an 'artist' to set here.
        // This really just corresponds to any directory.
        File artistDir = getNonEmptySubdir(new File(player.getRootPath()));
        String artist = artistDir.getName();
        List<FilesystemEntryBean> list = player.list(artist, null);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        for (FilesystemEntryBean thisAlbum : list){
            assertFalse(thisAlbum.isArtist);
            assertTrue(thisAlbum.isAlbum);
            assertFalse(thisAlbum.isSong);
            assertEquals(artist, thisAlbum.artist);
        }
    }

    @Test
    public void testListAlbum(){
        File artistDir = getSubdirWithNonEmptySubdir(new File(player.getRootPath()));
        String artistPath = artistDir.getPath();
        artistPath = artistPath.replace(player.getRootPath()+File.separator, "");
        int separatorIndex = artistPath.indexOf(File.separator);
        String artist = artistPath.substring(0, separatorIndex);
        String album = artistPath.substring(separatorIndex+1);
        List<FilesystemEntryBean> tracks = player.list(artist, album);
        assertNotNull(tracks);
        for (FilesystemEntryBean thisTrack : tracks){
            assertFalse(thisTrack.isArtist);
            assertFalse(thisTrack.isAlbum);
            assertTrue(thisTrack.isSong);
            assertEquals(artist, thisTrack.artist);
            assertEquals(album, thisTrack.album);
        }
    }

    @Test
    public void testPlaySong(){
        PlayingStatusBean status = player.play("artist1", "album1", "song1");
        assertTrue(status.isPlaying);
        assertEquals("artist1/album1/song1", status.name);
    }

    @Test
    public void testPlayAlbum(){
        PlayingStatusBean status = player.play("artist1", "album1", null);
        assertTrue(status.isPlaying);
        assertEquals("artist1/album1/null", status.name);
    }

    @Test
    public void testPause(){
        PlayingStatusBean status = player.play("artist1", "album1", "song1");
        assertTrue(status.isPlaying);
        status = player.pause();
        assertFalse(status.isPlaying);
        status = player.pause();
        assertTrue(status.isPlaying);
    }

    @Test
    public void testPlay(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.pause();
        assertFalse(status.isPlaying);
        status = player.play();
        assertTrue(status.isPlaying);
    }

    @Test
    public void testStop(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.stop();
        assertFalse(status.isPlaying);
    }

    @Test
    public void testGetStatus(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.getStatus();
        assertTrue(status.isPlaying);
        assertEquals("artist1/album1/song1", status.name);
    }

    @Test
    public void testNext(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.next();
        assertTrue(status.isPlaying);
    }

    @Test
    public void testPrevious(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.next();
        assertTrue(status.isPlaying);
    }

    @Test
    public void testSeek(){
        player.play("artist1", "album1", "song1");
        PlayingStatusBean status = player.seek(10);
        assertEquals(Long.valueOf(10), status.elapsedTime);
    }

    @Test
    public void testEventListener(){
        final PlayingStatusBean status = player.volume(10);
        player.listenForChanges(new PlayerListener() {
            @Override
            public void onChange(PlayingStatusBean state) {
                status.volume = state.volume;
            }

            @Override
            public void onPlaylistChange(PlaylistBean playlist) {
            }
        });
        assertEquals(Integer.valueOf(10), status.volume);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            //ok
        }
        assertEquals(Integer.valueOf(11), status.volume);
    }

    @Test
    public void testEmptyPlaylist(){
        assertTrue(player.getPlaylist().trackList.isEmpty());
    }

    @Test
    public void testSingleSongPlaylist(){
        player.play("artist1", "album1", "song1");
        PlaylistBean playlist = player.getPlaylist();
        assertEquals(1, playlist.trackList.size());
        assertEquals(Integer.valueOf(1), playlist.trackCount);
        assertEquals("artist1/album1/song1", playlist.currentTrackName);
    }

    @Test
    public void testSetVolume(){
        player.volume(-1);
        assertEquals(Integer.valueOf(0), player.getStatus().volume);
        player.volume(0);
        assertEquals(Integer.valueOf(0), player.getStatus().volume);
        player.volume(1);
        assertEquals(Integer.valueOf(1), player.getStatus().volume);
        player.volume(100);
        assertEquals(Integer.valueOf(100), player.getStatus().volume);
        player.volume(101);
        assertEquals(Integer.valueOf(100), player.getStatus().volume);
    }

    private File getNonEmptySubdir(File topLevelDir){
        // Find a non-empty directory in there.
        File subDir = null;
        for (File file : topLevelDir.listFiles()) {
            if (subDir == null && file.isDirectory() && file.listFiles().length > 0) {
                subDir = file;
            }
        }
        return subDir;
    }

    private File getSubdirWithNonEmptySubdir(File topLevelDir){
        File subDir = null;
        for (File file : topLevelDir.listFiles()){
            if (subDir == null && file.isDirectory() && getNonEmptySubdir(file)!=null){
                subDir = file;
            }
        }
        return getNonEmptySubdir(subDir);
    }

}
