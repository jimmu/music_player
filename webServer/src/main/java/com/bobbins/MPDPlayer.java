package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import org.bff.javampd.MPD;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 26/03/2016.
 */
public class MPDPlayer implements Player{

    private String nowPlayingPath = null;
    private Integer volume = 20;
    private MPD mpd;

    public MPDPlayer(){
        try {
            mpd = new MPD.Builder().build();
        } catch (MPDConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FilesystemEntryBean> list(String path) throws PlayerException {
        List<FilesystemEntryBean> files = new ArrayList<>();
        try {
            for(String file : mpd.getDatabase().listAllFiles()){
                files.add(new FilesystemEntryBean(file, 0, file, false, "files/list?path="+file, "play?path="+file));
            }
        } catch (MPDDatabaseException e) {
            e.printStackTrace();
            throw new PlayerException(e);
        }
        return files;
    }

    public PlayingStatusBean play(String playThis) throws PlayerException {
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        return getStatus();
    }

    public PlayingStatusBean getStatus() throws PlayerException {
        return new PlayingStatusBean(nowPlayingPath, volume);   //TODO. Use something nicer than the path.
    }

    public PlayingStatusBean volume(int volume) throws PlayerException {
        System.out.println("Set volume to "+volume);
        this.volume = volume;
        try {
            mpd.getPlayer().setVolume(volume);
        } catch (MPDPlayerException e) {
            throw new PlayerException(e);
        }
        return new PlayingStatusBean(nowPlayingPath, volume);
    }
}
