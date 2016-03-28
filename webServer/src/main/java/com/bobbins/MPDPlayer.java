package com.bobbins;

import com.bobbins.model.PlayingStatusBean;
import org.bff.javampd.MPD;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;

/**
 * Created by james on 26/03/2016.
 */
public class MPDPlayer implements Player{

    private String nowPlayingPath = null;
    private Integer volume = 20;
    private MPD mpd;

    public MPDPlayer(){
        try {
            mpd = new MPD.Builder().build();
        } catch (MPDConnectionException e) {
            e.printStackTrace();
        }
    }

    public PlayingStatusBean play(String playThis){
        System.out.println("Play: "+playThis);
        nowPlayingPath = playThis;
        return getStatus();
    }

    public PlayingStatusBean getStatus(){
        return new PlayingStatusBean(nowPlayingPath, volume);   //TODO. Use something nicer than the path.
    }

    public PlayingStatusBean volume(int volume){
        System.out.println("Set volume to "+volume);
        this.volume = volume;
        try {
            mpd.getPlayer().setVolume(volume);
        } catch (MPDPlayerException e) {
            e.printStackTrace(); //TODO. Rethrow something non MPD-specific
        }
        return new PlayingStatusBean(nowPlayingPath, volume);
    }
}
