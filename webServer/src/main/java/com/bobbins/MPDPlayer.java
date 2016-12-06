package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;
import org.bff.javampd.player.*;
import org.bff.javampd.server.MPD;
import org.bff.javampd.playlist.PlaylistBasicChangeListener;
import org.bff.javampd.playlist.PlaylistBasicChangeEvent;
import org.bff.javampd.server.MPDConnectionException;
import org.bff.javampd.artist.MPDArtist;
import org.bff.javampd.album.MPDAlbum;
import org.bff.javampd.song.MPDSong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.bff.javampd.player.Player.Status.STATUS_PAUSED;
import static org.bff.javampd.player.Player.Status.STATUS_PLAYING;

class MPDPlayer implements Player {

    private static final long COMMAND_COMPLETION_DELAY = 50;
    private MPD mpd;

    MPDPlayer() throws MPDConnectionException {
        mpd = new MPD.Builder().build();
    }

    @Override
    public PlaylistBean getPlaylist(){
        List<FilesystemEntryBean> playlist = new ArrayList<>();
        for (MPDSong song : mpd.getPlaylist().getSongList()){
            String artist = song.getArtistName();
            String album = song.getAlbumName();
            String songName = song.getName();
            playlist.add(new FilesystemEntryBean(artist, album, songName));
        }
        String songName = getStatus().name;
        return new PlaylistBean(playlist, songName);
    }

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) {
        List<FilesystemEntryBean> files = new ArrayList<>();
        if (artist == null || artist.isEmpty()){
            for (MPDArtist thisArtist: mpd.getMusicDatabase().getArtistDatabase().listAllArtists()){
                files.add(new FilesystemEntryBean(thisArtist.getName(), null, null));
            }
        }
        else {
            if (album == null || album.isEmpty()){
                for (MPDAlbum thisAlbum : mpd.getMusicDatabase().getAlbumDatabase().listAlbumsByArtist(new MPDArtist(artist))){
                    files.add(new FilesystemEntryBean(artist, thisAlbum.getName(), null));
                }
            }
            else{
                for (MPDSong song : mpd.getMusicDatabase().getSongDatabase().findAlbumByArtist(artist, album)){
                    files.add(new FilesystemEntryBean(artist, album, song.getName()));
                }
            }
        }
        return files;
    }

    public PlayingStatusBean play(String artist, String album, String song) {
        System.out.println("Play "+artist+"/"+album+"/"+song);
        mpd.getPlaylist().clearPlaylist();
        mpd.getPlaylist().addSongs(getSongs(artist, album, song));
        mpd.getPlayer().play();
        waitForCommand();
        return getStatus();
    }

    public PlayingStatusBean getStatus() {
        PlayingStatusBean status;
        MPDSong currentSong = mpd.getPlayer().getCurrentSong();
        String songName = (currentSong == null? "-- Nothing playing --" : currentSong.getName()); // Album and Artist also available here.
        int volume = mpd.getPlayer().getVolume();
        org.bff.javampd.player.Player.Status playerStatus = mpd.getPlayer().getStatus();
        Boolean isPlaying = STATUS_PLAYING.equals(playerStatus);
        Integer songLength = (currentSong == null? 60*60-1: currentSong.getLength());
        Long elapsedSeconds = (currentSong == null? 0 : mpd.getPlayer().getElapsedTime());
        status = new PlayingStatusBean(songName, volume, isPlaying, songLength, elapsedSeconds);
        return status;
    }

    public PlayingStatusBean volume(int volume) {
        int limitedVolume = Math.min(100, Math.max(0,volume));
        mpd.getPlayer().setVolume(limitedVolume);
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean pause() {
        org.bff.javampd.player.Player.Status playerStatus = mpd.getPlayer().getStatus();
        if (STATUS_PAUSED.equals(playerStatus)){
            mpd.getPlayer().play();
        }
        else if (STATUS_PLAYING.equals(playerStatus)){
            mpd.getPlayer().pause();
        }
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean stop() {
        mpd.getPlayer().stop();
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean next() {
        mpd.getPlayer().playNext();
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean previous() {
        mpd.getPlayer().playPrevious();
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean play() {
        mpd.getPlayer().play();
        waitForCommand();
        return getStatus();
    }

    @Override
    public PlayingStatusBean seek(int positionInSeconds) {
        mpd.getPlayer().seek(positionInSeconds);
        waitForCommand();
        return getStatus();
    }

    private List<MPDSong> getSongs(String artist, String album, String song) {
        List<MPDSong> songs = new ArrayList<>();
        if (artist != null && album != null && !artist.trim().isEmpty() && !album.trim().isEmpty()) {
            Collection<MPDSong> allSongs = mpd.getMusicDatabase().getSongDatabase().findAlbumByArtist(artist, album);
            if (song == null || song.trim().isEmpty()) {
                songs.addAll(allSongs);
            }
            else{
                for (MPDSong thisSong : allSongs){
                    if (song.equals(thisSong.getTitle())){
                        songs.add(thisSong);
                    }
                }
            }
        }
        return songs;
    }

    public void listenForChanges(final PlayerListener listener) {
        //Register ourselves with MPD for event changes and then pass those on.
        mpd.getMonitor().addPlayerChangeListener(new PlayerBasicChangeListener(){
            public void playerBasicChange(PlayerBasicChangeEvent event){
                //Do we care about the content of the event?
                listener.onChange(getStatus());
            }
        });

        mpd.getMonitor().addPlaylistChangeListener(new PlaylistBasicChangeListener(){
            public void playlistBasicChange(PlaylistBasicChangeEvent event){
                //Do we care about the content of the event?
                listener.onPlaylistChange(getPlaylist());
            }
        });
        mpd.getMonitor().addTrackPositionChangeListener(new TrackPositionChangeListener() {
            @Override
            public void trackPositionChanged(TrackPositionChangeEvent trackPositionChangeEvent) {
                listener.onChange(getStatus());
            }
        });
        mpd.getMonitor().addVolumeChangeListener(new VolumeChangeListener() {
            @Override
            public void volumeChanged(VolumeChangeEvent volumeChangeEvent) {
                listener.onChange(getStatus());
            }
        });
        mpd.getMonitor().start();
    }

    private void waitForCommand(){
        try{
            Thread.sleep(COMMAND_COMPLETION_DELAY);
        }
        catch(InterruptedException e){
            //ok
        }
    }
}
