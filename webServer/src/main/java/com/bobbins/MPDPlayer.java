package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import org.bff.javampd.MPD;
import org.bff.javampd.MPDFile;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.monitor.PlayerStatus;
import org.bff.javampd.objects.MPDArtist;
import org.bff.javampd.objects.MPDAlbum;
import org.bff.javampd.objects.MPDSong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by james on 26/03/2016.
 */
public class MPDPlayer implements Player{

    private MPD mpd;

    public MPDPlayer() throws MPDConnectionException {
        mpd = new MPD.Builder().build();
    }

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) throws PlayerException {
        List<FilesystemEntryBean> files = new ArrayList<>();
        try{
            if (artist == null || artist.isEmpty()){
                for (MPDArtist thisArtist: mpd.getDatabase().listAllArtists()){
                    files.add(new FilesystemEntryBean(thisArtist.getName(), null, null));
                }
            }
            else {
                if (album == null || album.isEmpty()){
                    for (MPDAlbum thisAlbum : mpd.getDatabase().listAlbumsByArtist(new MPDArtist(artist))){
                        files.add(new FilesystemEntryBean(artist, thisAlbum.getName(), null));
                    }
                }
                else{
                    for (MPDSong song : mpd.getDatabase().findAlbumByArtist(new MPDArtist(artist), new MPDAlbum(album))){
                        files.add(new FilesystemEntryBean(artist, album, song.getName()));
                    }
                }
            }
        }
        catch(MPDDatabaseException e){
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return files;
    }

    public PlayingStatusBean play(String artist, String album, String song) throws PlayerException {
        System.out.println("Play "+artist+"/"+album+"/"+song);
        try {
            mpd.getPlaylist().clearPlaylist();
            mpd.getPlaylist().addSongs(getSongs(artist, album, song));
            mpd.getPlayer().play();
        } catch (MPDPlaylistException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        } catch (MPDDatabaseException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        } catch (MPDPlayerException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return getStatus();
    }

    public PlayingStatusBean getStatus() throws PlayerException {
	PlayingStatusBean status = null;
	try {
	    MPDSong currentSong = mpd.getPlayer().getCurrentSong();
	    String songName = currentSong.getName(); // Album and Artist also available here.
	    int volume = mpd.getPlayer().getVolume();
        org.bff.javampd.Player.Status playerStatus = mpd.getPlayer().getStatus();
        Boolean isPlaying = PlayerStatus.STATUS_PLAYING.equals(playerStatus);
        status = new PlayingStatusBean(songName, volume, isPlaying);
        } catch (MPDPlayerException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return status;
    }

    public PlayingStatusBean volume(int volume) throws PlayerException {
        int limitedVolume = Math.min(100, Math.max(0,volume));
        try {
            mpd.getPlayer().setVolume(limitedVolume);
        } catch (MPDPlayerException e) {
            throw new PlayerException(e);
        }
        return getStatus();
    }

    @Override
    public PlayingStatusBean pause() throws PlayerException {
        try {
            org.bff.javampd.Player.Status playerStatus = mpd.getPlayer().getStatus();
            if (org.bff.javampd.Player.Status.STATUS_PAUSED.equals(playerStatus)){
                mpd.getPlayer().play();
            }
            else if (org.bff.javampd.Player.Status.STATUS_PLAYING.equals(playerStatus)){
                mpd.getPlayer().pause();
            }
        } catch (MPDPlayerException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return getStatus();
    }

    @Override
    public PlayingStatusBean stop() throws PlayerException {
        try {
            mpd.getPlayer().stop();
        } catch (MPDPlayerException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return getStatus();
    }

    private List<MPDSong> getSongs(String artist, String album, String song) throws MPDDatabaseException {
        List<MPDSong> songs = new ArrayList<>();
        if (artist != null && album != null && !artist.trim().isEmpty() && !album.trim().isEmpty()) {
            Collection<MPDSong> allSongs = mpd.getDatabase().findAlbumByArtist(new MPDArtist(artist), new MPDAlbum(album));
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
}
