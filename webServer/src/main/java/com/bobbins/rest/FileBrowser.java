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
        // Get data from the player. Then enrich it with restful urls.
        // We don't want the player having to do that itself.
        System.out.println("Listing "+artist+"/"+album);
        com.bobbins.Player player = PlayerFactory.getPlayer();
        List<FilesystemEntryBean> musicEntries = player.list(artist, album);
        for (FilesystemEntryBean entry : musicEntries){
            //Enrich this entry with urls.
            if (entry.artist != null ){
               entry.setPlayActionUrl(null);
               entry.setListActionUrl("list/"+entry.artist);
                entry.setName(entry.artist);
               if (entry.album != null){
                   entry.setPlayActionUrl("play/"+entry.artist+"/"+entry.album);
                   entry.setListActionUrl(entry.listActionUrl+"/"+entry.album);
                   entry.setName(entry.album);
                   if (entry.song != null){
                       entry.setPlayActionUrl(entry.playActionUrl+"/"+entry.song);
                       entry.setListActionUrl(null);
                       entry.setName(entry.song);
                   }
               }
            }
        }
        return musicEntries;
    }

}
