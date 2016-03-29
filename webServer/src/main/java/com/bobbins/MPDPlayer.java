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

    private MPD mpd;

    public MPDPlayer() throws MPDConnectionException {
        mpd = new MPD.Builder().build();
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
        return null;
    }

    public PlayingStatusBean getStatus() throws PlayerException {
        return null;
    }

    public PlayingStatusBean volume(int volume) throws PlayerException {
        try {
            mpd.getPlayer().setVolume(volume);
        } catch (MPDPlayerException e) {
            throw new PlayerException(e);
        }
        return new PlayingStatusBean("foo", volume);
    }
}
