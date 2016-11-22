package com.bobbins.rest;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.PlayerException;
import com.bobbins.PlayerFactory;
import com.bobbins.PlayerListener;
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
        final PlayerListener listener = new PlayerListener(){
            public void onChange(PlayingStatusBean state){
                try {
                    final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                    eventBuilder.name("player-state-change");
                    eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
                    eventBuilder.data(PlayingStatusBean.class, state);
                    final OutboundEvent event = eventBuilder.build();
                    if (eventOutput.isClosed()){    //TODO. Work out if/when we should close this.
                        System.out.println("Oh - the EventOutput object is closed!");
                        System.out.println("*NOT* writing: "+event);
                    }
                    else {
                        eventOutput.write(event);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(
                        "Error when writing the event.", e);
                } //finally {
                        //try {
                            //eventOutput.close();
                        //} catch (IOException ioClose) {
                            //throw new RuntimeException(
                                //"Error when closing the event output.", ioClose);
                        //}
                    //}
            }
        };
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
