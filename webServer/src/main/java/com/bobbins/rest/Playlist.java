package com.bobbins.rest;

import com.bobbins.PlayerFactory;
import com.bobbins.model.FilesystemEntryBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("playlist")
public class Playlist {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilesystemEntryBean> playlist(){
        return PlayerFactory.getPlayer().getPlaylist();
    }
}
