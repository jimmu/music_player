package com.bobbins;

import com.bobbins.model.PlayingStatusBean;

/**
 * Created by james on 26/03/2016.
 */
public class Player {

    private String nowPlayingPath = null;

    public PlayingStatusBean play(String playThis){
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        return getStatus();
    }

    public PlayingStatusBean getStatus(){
        return new PlayingStatusBean(nowPlayingPath);   //TODO. Use something nicer than the path.
    }
}
