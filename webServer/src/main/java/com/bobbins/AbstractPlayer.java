package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;

import java.util.List;

public abstract class AbstractPlayer implements  Player{
    @Override
    public PlaylistBean getPlaylist() {
        return null;
    }

    @Override
    public List<FilesystemEntryBean> list(String artist, String album) {
        return null;
    }

    @Override
    public PlayingStatusBean play(String artist, String album, String song) {
        return null;
    }

    @Override
    public PlayingStatusBean getStatus() {
        return null;
    }

    @Override
    public PlayingStatusBean volume(int volume) {
        return null;
    }

    @Override
    public PlayingStatusBean pause() {
        return null;
    }

    @Override
    public PlayingStatusBean stop() {
        return null;
    }

    @Override
    public void listenForChanges(PlayerListener listener) {

    }

    @Override
    public PlayingStatusBean next() {
        return null;
    }

    @Override
    public PlayingStatusBean previous() {
        return null;
    }

    @Override
    public PlayingStatusBean play() {
        return null;
    }

    @Override
    public PlayingStatusBean seek(int positionInSeconds) {
        return null;
    }
}
