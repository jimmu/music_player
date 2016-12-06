package com.bobbins.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.bobbins.EventSendingListener;
import com.bobbins.PlayerFactory;
import com.bobbins.PlayerListener;
import com.bobbins.model.FilesystemEntryBean;
import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

import java.util.List;

@Path("play")
public class Player {

    private com.bobbins.Player player;

    public Player(){
      this(PlayerFactory.getPlayer());
    }

    public Player(com.bobbins.Player player){
      this.player = player;
    }

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
        return player.play(artist, album, song);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public PlayingStatusBean status() {
        return player.getStatus();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("volume")
    public PlayingStatusBean quieter(@QueryParam("volume") Integer volume) {
        return player.volume(volume);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seek")
    public PlayingStatusBean seek(@QueryParam("position") Integer positionInSeconds) {
        return player.seek(positionInSeconds);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pause")
    public PlayingStatusBean pause() {
        return player.pause();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stop")
    public PlayingStatusBean stop() {
        return player.stop();
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("events")
    public EventOutput getServerSentEvents(){
	final EventOutput eventOutput = new EventOutput();
        System.out.println("**** Creating a new event sender");
        PlayerListener listener = new EventSendingListener(eventOutput);
        player.listenForChanges(listener);
        return eventOutput;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("next")
    public PlayingStatusBean next() {
        return player.next();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("previous")
    public PlayingStatusBean previous() {
        return player.previous();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("play")
    public PlayingStatusBean play() {
        return player.play();
    }
}
