package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import org.bff.javampd.exception.MPDPlayerException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 29/03/2016.
 */
public class PlainPlayer implements Player {
    private String rootPath = null;
    private String nowPlayingPath = null;
    private Integer volume = 20;

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) throws PlayerException {
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try {
            if (rootPath == null) {
                rootPath = new File(".").getCanonicalPath();
            }
            String path = artist + File.separator + "album";
            File[] allFilesAndDirs = new File(path).listFiles();
            if (allFilesAndDirs != null) {
                for (File file : allFilesAndDirs) {
                    String fullPath = file.getCanonicalPath();
                    String shortPath = fullPath.substring(rootPath.length() + 1);
                    String listUrl = (file.isDirectory()? "files/list?path=" + shortPath : null);
                    files.add(new FilesystemEntryBean(file.getName(),
                            fullPath.hashCode(),
                            shortPath,
                            !file.isDirectory(),
                            listUrl,
                            "play?path=" + shortPath));
                }
            }
        }
        catch(IOException e){
            files.add(new FilesystemEntryBean(e.toString(), 0, "", false, "", ""));
        }
        return files;

    }

    @Override
    public PlayingStatusBean play(String artist, String album, String song) throws PlayerException {
        String playThis = artist+":"+album+":"+song;
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        return getStatus();
    }

    @Override
    public PlayingStatusBean getStatus() throws PlayerException {
        return new PlayingStatusBean(nowPlayingPath, volume);   //TODO. Use something nicer than the path.
    }

    @Override
    public PlayingStatusBean volume(int volume) throws PlayerException {
        System.out.println("Set volume to "+volume);
        this.volume = volume;
        return new PlayingStatusBean(nowPlayingPath, volume);
    }
}
