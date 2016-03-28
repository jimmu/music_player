package com.bobbins;

/**
 * Created by james on 26/03/2016.
 */
public class PlayerFactory {

    private static MPDPlayer instance = new MPDPlayer();    //Linux specific. TODO - make this cope with other OSes or tests

    public static Player getPlayer(){
        return instance;
    }
}
