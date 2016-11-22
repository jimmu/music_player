package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import org.bff.javampd.exception.MPDPlayerException;

import java.io.File;
import java.io.IOException;
import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Created by james on 29/03/2016.
 */
public class PlainPlayer implements Player {
    private String rootPath = null;
    private String nowPlayingPath = null;
    private Integer volume = 20;
    private boolean isPlaying = false;
    private long songStartTime = System.currentTimeMillis();

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) throws PlayerException {
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try {
            if (rootPath == null) {
                if (System.getProperty("os.name").toLowerCase().contains("windows")){
                    rootPath = System.getProperty("user.home");
                }
                else {
                    rootPath = new File(".").getCanonicalPath();
                }
            }
            String path = rootPath;
            if (artist != null){
                path = path + File.separator + artist;
                if (album != null){
                    path = path + File.separator + album;
                }
            }
            File[] allFilesAndDirs = new File(path).listFiles();
            if (allFilesAndDirs != null) {
                String pathSplitter = ("\\".equals(File.separator)? "\\\\" : File.separator);
                for (File file : allFilesAndDirs) {
                    try {
                        String fullName = file.getPath().substring(rootPath.length()+File.separator.length());
                        String[] bits = fullName.split(pathSplitter);
                        String thisArtist = null;
                        String thisAlbum = null;
                        String song = null;
                        if (bits.length > 0) {
                            thisArtist = bits[0];
                            if (bits.length > 1) {
                                thisAlbum = bits[1];
                                if (bits.length > 2) {
                                    song = bits[2];
                                }
                            }
                        }
			if (thisArtist == null || "".equals(thisArtist)){
			    thisArtist = "x";
			}
                        FilesystemEntryBean entry = new FilesystemEntryBean(thisArtist, thisAlbum, song);
                        entry.setName(file.getPath());
                        entry.setListActionUrl("list/"+(song != null ? song : (thisAlbum != null ? thisAlbum : thisArtist)));
                        entry.setPlayActionUrl("play/"+(song != null ? song : (thisAlbum != null ? thisAlbum : thisArtist)));
                        files.add(entry);
                    }
                    catch (PatternSyntaxException e){
                        System.out.println("Duff pattern in call to String.split "+e);
                    }
                }
            }
        }
        catch(IOException e){
            files.add(new FilesystemEntryBean(e.toString(), "oh", "dear"));
        }
        return files;

    }

    @Override
    public PlayingStatusBean play(String artist, String album, String song) throws PlayerException {
        String playThis = artist+":"+album+":"+song;
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        isPlaying = true;
        songStartTime = System.currentTimeMillis();
        return getStatus();
    }

    @Override
    public PlayingStatusBean getStatus() throws PlayerException {
	Integer songLength = 182;
	Long elapsedSeconds = (System.currentTimeMillis()-songStartTime)/1000; //A hack.
        return new PlayingStatusBean(nowPlayingPath, volume, isPlaying, songLength, elapsedSeconds);   //TODO. Use something nicer than the path.
    }

    @Override
    public PlayingStatusBean volume(int volume) throws PlayerException {
        this.volume = Math.min(100, Math.max(0,volume));
        System.out.println("Set volume to "+this.volume);
        return getStatus();
    }

    @Override
    public PlayingStatusBean pause() throws PlayerException {
        isPlaying = !isPlaying;
        return getStatus();
    }

    @Override
    public PlayingStatusBean stop() throws PlayerException {
        isPlaying = false;
        return getStatus();
    }

    @Override
    public void listenForChanges(final PlayerListener listener) throws PlayerException {
        // Send made-up changes every now and again.
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        try {
                            volume++;
                            listener.onChange(getStatus());
                        } catch (PlayerException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    public PlayingStatusBean next() throws PlayerException {
        return getStatus();
    }

    @Override
    public PlayingStatusBean previous() throws PlayerException {
        return getStatus();
    }

    @Override
    public PlayingStatusBean play() throws PlayerException {
        isPlaying = true;
        return getStatus();
    }

    @Override
    public PlayingStatusBean seek(int positionInSeconds) throws PlayerException {
        songStartTime = System.currentTimeMillis()-(positionInSeconds*1000);
        return getStatus();
    }
}
