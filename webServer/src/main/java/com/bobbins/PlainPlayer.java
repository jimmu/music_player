package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

class PlainPlayer implements Player {
    private String rootPath = null;
    private String nowPlayingArtist;
    private String nowPlayingAlbum;
    private String nowPlayingSong;
    private Integer volume = 20;
    private boolean isPlaying = false;
    private long songStartTime = System.currentTimeMillis();

    @Override
    public List<FilesystemEntryBean> getPlaylist(){
        if (nowPlayingArtist == null){
            return new ArrayList<>();
        }
        if (nowPlayingSong != null){
            return new ArrayList<>(Collections.singletonList(new FilesystemEntryBean(nowPlayingArtist, nowPlayingAlbum, nowPlayingSong)));
        }
        return list(nowPlayingArtist, nowPlayingAlbum);
    }

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) {
        List<FilesystemEntryBean> files = new ArrayList<>();
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
                        files.add(new FilesystemEntryBean(thisArtist, thisAlbum, song));
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
    public PlayingStatusBean play(String artist, String album, String song) {
        nowPlayingArtist = artist;
        nowPlayingAlbum = album;
        nowPlayingSong = song;
        System.out.println("Play: "+artist+"/"+album+"/"+song);
        isPlaying = true;
        songStartTime = System.currentTimeMillis();
        return getStatus();
    }

    @Override
    public PlayingStatusBean getStatus() {
        Integer songLength = 182;
        Long elapsedSeconds = (System.currentTimeMillis()-songStartTime)/1000; //A hack.
        String name = nowPlayingArtist+"/"+nowPlayingAlbum+"/"+nowPlayingSong;
        name = (nowPlayingSong == null? "-- Nothing playing --" : name);
        return new PlayingStatusBean(name, volume, isPlaying, songLength, elapsedSeconds);
    }

    @Override
    public PlayingStatusBean volume(int volume) {
        this.volume = Math.min(100, Math.max(0,volume));
        System.out.println("Set volume to "+this.volume);
        return getStatus();
    }

    @Override
    public PlayingStatusBean pause() {
        isPlaying = !isPlaying;
        return getStatus();
    }

    @Override
    public PlayingStatusBean stop() {
        isPlaying = false;
        return getStatus();
    }

    @Override
    public void listenForChanges(final PlayerListener listener) {
        // Send made-up changes every now and again.
        new Thread(new Runnable() {
            public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                    volume++;
                    listener.onChange(getStatus());
                } catch (InterruptedException e) {
                    //ok
                }
            }
            }
        }).start();
    }

    public PlayingStatusBean next() {
        return getStatus();
    }

    @Override
    public PlayingStatusBean previous() {
        return getStatus();
    }

    @Override
    public PlayingStatusBean play() {
        isPlaying = true;
        return getStatus();
    }

    @Override
    public PlayingStatusBean seek(int positionInSeconds) {
        songStartTime = System.currentTimeMillis()-(positionInSeconds*1000);
        return getStatus();
    }
}
