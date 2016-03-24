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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;

@Path("gui")
public class Gui {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream gui(){
      // Note the leading slash.
      InputStream is = Gui.class.getResourceAsStream("/index.html");
      return is;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("js")
    public InputStream script(){
      InputStream is = Gui.class.getResourceAsStream("/player.js");
      return is;
    }

}
