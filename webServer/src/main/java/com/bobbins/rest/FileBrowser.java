package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bobbins.*;
import com.bobbins.model.FilesystemEntryBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

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
        // Get data from the player. Then enrich it with restful urls.
        // We don't want the player having to do that itself.
        System.out.println("Listing "+artist+"/"+album);
        com.bobbins.Player player = PlayerFactory.getPlayer();
        try {
            List<FilesystemEntryBean> musicEntries = player.list(artist, album);
            for (FilesystemEntryBean entry : musicEntries){
                //Enrich this entry with urls.
                if (entry.artist != null ){
                   entry.setPlayActionUrl("play/"+entry.artist);
                   entry.setListActionUrl("list/"+entry.artist);
                   if (entry.album != null){
                       entry.setPlayActionUrl(entry.playActionUrl+"/"+entry.album);
                       entry.setListActionUrl(entry.listActionUrl+"/"+entry.album);
                       if (entry.song != null){
                           entry.setPlayActionUrl(entry.playActionUrl+"/"+entry.song);
                           entry.setListActionUrl(null);
                       }
                   }
                }
            }
            return musicEntries;
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO return a 500
    }

}
