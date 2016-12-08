package com.bobbins.rest;

import com.bobbins.AbstractPlayer;
import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private com.bobbins.Player player;

    @Before
    public void setup(){
        player = new AbstractPlayer(){
            @Override
            public PlayingStatusBean play(String artist, String album, String song) {
                String songName = song != null? song : "song1";
                return new PlayingStatusBean(songName, 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean getStatus() {
                return new PlayingStatusBean("song1", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean volume(int volume) {
                return new PlayingStatusBean("song1", volume, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean pause() {
                return new PlayingStatusBean("pause", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean stop() {
                return new PlayingStatusBean("stop", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean next() {
                return new PlayingStatusBean("next", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean previous() {
                return new PlayingStatusBean("previous", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean play() {
                return new PlayingStatusBean("play", 50, true, 100, 0L);
            }

            @Override
            public PlayingStatusBean seek(int positionInSeconds) {
                return new PlayingStatusBean("seek", 50, true, 100, (long)positionInSeconds);
            }

        };
    }

    @Test
    public void testPlaySong(){
        Player playerService = new Player(player);
        PlayingStatusBean status = playerService.play("artist1", "album1", "woohoo");
        assertEquals("woohoo", status.name);
        assertTrue(status.isPlaying);
    }

    @Test
    public void testPlayAlbum(){
        Player playerService = new Player(player);
        PlayingStatusBean status = playerService.play("artist1", "album1");
        assertEquals("song1", status.name);
        assertTrue(status.isPlaying);
    }

    @Test
    public void testPlayArtist(){
        Player playerService = new Player(player);
        PlayingStatusBean status = playerService.play("artist1");
        assertEquals("song1", status.name);
        assertTrue(status.isPlaying);
    }

    @Test
    public void testGetStatus(){
        Player playerService = new Player(player);
        assertEquals("song1", playerService.status().name);
    }

    @Test
    public void testVolume(){
        Player playerService = new Player(player);
        assertEquals(Integer.valueOf(12), playerService.quieter(12).volume);
    }

    @Test
    public void testPause(){
        Player playerService = new Player(player);
        assertEquals("pause", playerService.pause().name);
    }

    @Test
    public void testStop(){
        Player playerService = new Player(player);
        assertEquals("stop", playerService.stop().name);
    }

    @Test
    public void testNext(){
        Player playerService = new Player(player);
        assertEquals("next", playerService.next().name);
    }

    @Test
    public void testPrevious(){
        Player playerService = new Player(player);
        assertEquals("previous", playerService.previous().name);
    }

    @Test
    public void testPlay(){
        Player playerService = new Player(player);
        assertEquals("play", playerService.play().name);
    }

    @Test
    public void testSeek(){
        Player playerService = new Player(player);
        assertEquals(Long.valueOf(123), playerService.seek(123).elapsedTime);
    }

    @Test
    public void testGetServerSentEvents(){
        Player playerService = new Player(player);
        EventOutput eventOut = playerService.getServerSentEvents();
        assertNotNull(eventOut);
    }
}
