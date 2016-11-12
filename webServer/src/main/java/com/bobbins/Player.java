package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;

import java.util.List;

/**
 * Created by james on 26/03/2016.
 */
public interface Player {

    public List<FilesystemEntryBean> list(String artist, String album) throws PlayerException;

    public PlayingStatusBean play(String artist, String album, String song) throws PlayerException;
    public PlayingStatusBean getStatus() throws PlayerException;
    public PlayingStatusBean volume(int volume) throws PlayerException;
    public PlayingStatusBean pause() throws PlayerException;
}
