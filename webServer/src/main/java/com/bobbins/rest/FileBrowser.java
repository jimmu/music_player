package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bobbins.*;
import com.bobbins.model.FilesystemEntryBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

@Path("files")
public class FileBrowser {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilesystemEntryBean> rootList() {
      return list("."); //TODO. Find the root of the music tree
    }
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public List<FilesystemEntryBean> list(@QueryParam("path") String path) {
        System.out.println("Listing "+path);
        com.bobbins.Player player = PlayerFactory.getPlayer();
        try {
            return player.list(path);
        } catch (PlayerException e) {
            e.printStackTrace();
        }
        return null; //TODO return a 500
    }

}
