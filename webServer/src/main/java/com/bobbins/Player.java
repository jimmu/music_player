package com.bobbins;

import com.bobbins.model.PlayingStatusBean;

/**
 * Created by james on 26/03/2016.
 */
public class Player {

    private String nowPlayingPath = null;
    private Integer volume = 20;

    public PlayingStatusBean play(String playThis){
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        return getStatus();
    }

    public PlayingStatusBean getStatus(){
        return new PlayingStatusBean(nowPlayingPath, volume);   //TODO. Use something nicer than the path.
    }

    public PlayingStatusBean volumeUp(){
        System.out.println("Volume up");
        volume++;
        return new PlayingStatusBean(nowPlayingPath, volume);
    }

    public PlayingStatusBean volumeDown(){
        System.out.println("Volume down");
        volume--;
        return new PlayingStatusBean(nowPlayingPath, volume);
    }

    public PlayingStatusBean volume(int volume){
        System.out.println("Set volume to "+volume);
        this.volume = volume;
        return new PlayingStatusBean(nowPlayingPath, volume);
    }
}
