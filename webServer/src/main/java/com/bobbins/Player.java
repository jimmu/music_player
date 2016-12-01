package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;

import java.util.List;

/**
 * Created by james on 26/03/2016.
 */
public interface Player {

    List<FilesystemEntryBean> getPlaylist();
    List<FilesystemEntryBean> list(String artist, String album) ;
    PlayingStatusBean play(String artist, String album, String song) ;
    PlayingStatusBean getStatus() ;
    PlayingStatusBean volume(int volume) ;
    PlayingStatusBean pause() ;
    PlayingStatusBean stop() ;
    void listenForChanges(PlayerListener listener) ;
    PlayingStatusBean next() ;
    PlayingStatusBean previous() ;
    PlayingStatusBean play() ;
    PlayingStatusBean seek(int positionInSeconds) ;
}
