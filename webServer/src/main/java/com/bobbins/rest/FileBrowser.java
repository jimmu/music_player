package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bobbins.*;
import com.bobbins.model.FilesystemEntryBean;
import java.util.List;

@Path("list")
public class FileBrowser {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilesystemEntryBean> rootList() {
      return list(null, null); 
    }
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artist}")
    public List<FilesystemEntryBean> list(@PathParam("artist") String artist){
        return list(artist, null);
    }
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artist}/{album}")
    public List<FilesystemEntryBean> list(@PathParam("artist") String artist,
                                          @PathParam("album") String album) {
        System.out.println("Listing "+artist+"/"+album);
        com.bobbins.Player player = PlayerFactory.getPlayer();
        return player.list(artist, album);
    }

}
