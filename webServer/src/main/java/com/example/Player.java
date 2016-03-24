package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.example.model.PlayingStatusBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

@Path("play")
public class Player {

    private String rootPath = null;
    private String nowPlayingPath = null;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlayingStatusBean play(@QueryParam("path") String path) {
      System.out.println("Play: "+path);
      nowPlayingPath = path;
      return status();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public PlayingStatusBean status() {
      //TODO - the state in this class is lost between calls.
      //Which is entirely right and proper.
      //So think about where the player state _should_ be maintained.
      return new PlayingStatusBean(nowPlayingPath);
    }
 
}
