package com.bobbins;

/**
 * Created by james on 26/03/2016.
 */
public class PlayerFactory {

    private static Player instance = new Player();

    public static Player getPlayer(){
        return instance;
    }
}
