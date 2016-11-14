package com.bobbins.rest;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.PlayerException;
import com.bobbins.PlayerFactory;
import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        try { Thread.sleep(5000); } catch(InterruptedException e){}
                        final OutboundEvent.Builder eventBuilder
                        = new OutboundEvent.Builder();
                        eventBuilder.name("player-state-change");
                        eventBuilder.data(String.class,
                            "Hello world " + i + "!");
                        final OutboundEvent event = eventBuilder.build();
                        eventOutput.write(event);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(
                        "Error when writing the event.", e);
                } finally {
                    try {
                        eventOutput.close();
                    } catch (IOException ioClose) {
                        throw new RuntimeException(
                            "Error when closing the event output.", ioClose);
                    }
                }
            }
        }).start();
        return eventOutput;
    }
}
