package com.bobbins;

import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;

public interface PlayerListener {

    void onChange(PlayingStatusBean state);
    void onPlaylistChange(PlaylistBean playlist);
}
