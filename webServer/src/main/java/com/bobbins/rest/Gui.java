package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("d3")
    public InputStream library(){
      InputStream is = Gui.class.getResourceAsStream("/d3.v3.min.js");
      return is;
    }
}
