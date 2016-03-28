package com.bobbins;

import com.bobbins.model.PlayingStatusBean;

/**
 * Created by james on 26/03/2016.
 */
public interface Player {

    public PlayingStatusBean play(String playThis);
    public PlayingStatusBean getStatus();
    public PlayingStatusBean volume(int volume);
}
