package com.bobbins;

import org.glassfish.hk2.api.Factory;

public class PlayerFactory implements Factory<Player> {

    private static Player instance = null;

    @Override
    public Player provide() {
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

    @Override
    public void dispose(Player player) {
        //Nothing to do.
    }
}
