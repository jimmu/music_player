package com.bobbins.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.EventSendingListener;
import com.bobbins.PlayerException;
import com.bobbins.PlayerFactory;
import com.bobbins.PlayerListener;
import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seek")
    public PlayingStatusBean seek(@QueryParam("position") Integer positionInSeconds) {
        try {
            return PlayerFactory.getPlayer().seek(positionInSeconds);
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pause")
    public PlayingStatusBean pause() {
        try {
            return PlayerFactory.getPlayer().pause();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stop")
    public PlayingStatusBean stop() {
        try {
            return PlayerFactory.getPlayer().stop();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("events")
    public EventOutput getServerSentEvents(){
	final EventOutput eventOutput = new EventOutput();
        System.out.println("**** Creating a new event sender");
        PlayerListener listener = new EventSendingListener(eventOutput);
        try {
	        PlayerFactory.getPlayer().listenForChanges(listener);
    	}
        catch(PlayerException e){
            e.printStackTrace();
        }
        return eventOutput;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("next")
    public PlayingStatusBean next() {
        try {
            return PlayerFactory.getPlayer().next();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("previous")
    public PlayingStatusBean previous() {
        try {
            return PlayerFactory.getPlayer().previous();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("play")
    public PlayingStatusBean play() {
        try {
            return PlayerFactory.getPlayer().play();
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO. Return a 500 series error
    }
}
