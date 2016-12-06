package com.bobbins;

import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;

import java.util.List;

public interface PlayerListener {

    public void onChange(PlayingStatusBean state);
    public void onPlaylistChange(List<FilesystemEntryBean> playlist);
}
