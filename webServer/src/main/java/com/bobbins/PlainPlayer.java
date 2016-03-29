package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 29/03/2016.
 */
public class PlainPlayer implements Player {
    private String rootPath = null;

    @Override
    public List<FilesystemEntryBean> list(String path) throws PlayerException {
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try {
            if (rootPath == null) {
                rootPath = new File(".").getCanonicalPath();
            }
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
    public PlayingStatusBean play(String playThis) throws PlayerException {
        return null;
    }

    @Override
    public PlayingStatusBean getStatus() throws PlayerException {
        return null;
    }

    @Override
    public PlayingStatusBean volume(int volume) throws PlayerException {
        return null;
    }
}
