package com.bobbins.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.PlayerFactory;
import com.bobbins.model.PlayingStatusBean;

@Path("play")
public class Player {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("play")
    public PlayingStatusBean play(@QueryParam("path") String path) {
        return PlayerFactory.getPlayer().play(path);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public PlayingStatusBean status() {
        return PlayerFactory.getPlayer().getStatus();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("volume")
    public PlayingStatusBean quieter(@QueryParam("volume") Integer volume) {
        return PlayerFactory.getPlayer().volume(volume);
    }

}
