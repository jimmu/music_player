package com.bobbins;

/**
 * Created by james on 26/03/2016.
 */
public class PlayerFactory {

    private static Player instance = null;

    public static synchronized Player getPlayer(){
        if (instance == null){
            try {
                instance = new MPDPlayer();
                instance.list(".", null);
            } catch (Exception e) {
                System.out.println("No MPD - falling back to filesystem");
                instance = new PlainPlayer();
            }
        }
        return instance;
    }
}
