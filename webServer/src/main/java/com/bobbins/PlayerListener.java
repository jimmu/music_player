package com.bobbins;

import com.bobbins.model.PlayingStatusBean;

public interface PlayerListener {

    public void onChange(PlayingStatusBean state);
}