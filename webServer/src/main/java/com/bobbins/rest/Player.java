package com.bobbins.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.PlayerException;
import com.bobbins.PlayerFactory;
import com.bobbins.model.PlayingStatusBean;

@Path("play")
public class Player {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("play")
    public PlayingStatusBean play(@QueryParam("path") String path) {
        try {
            return PlayerFactory.getPlayer().play(path);
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public PlayingStatusBean status() {
        try {
            return PlayerFactory.getPlayer().getStatus();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("volume")
    public PlayingStatusBean quieter(@QueryParam("volume") Integer volume) {
        try {
            return PlayerFactory.getPlayer().volume(volume);
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

}
