package com.bobbins.rest;

import com.bobbins.Player;
import com.bobbins.model.PlaylistBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("playlist")
public class Playlist {

    private Player player;

    @Inject
    public Playlist(Player player){
      this.player = player;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistBean playlist(){
        return player.getPlaylist();
    }
}
