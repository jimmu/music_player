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
    @Path("{artist}")
    public PlayingStatusBean play(@PathParam("artist") String artist){
        return play(artist, null);
    }
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artist}/{album}")
    public PlayingStatusBean play(@PathParam("artist") String artist,
                                  @PathParam("album") String album){
        return play(artist, album, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artist}/{album}/{song}")
    public PlayingStatusBean play(@PathParam("artist") String artist,
                                  @PathParam("album") String album,
                                  @PathParam("song") String song){
        try {
            return PlayerFactory.getPlayer().play(artist, album, song);
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
