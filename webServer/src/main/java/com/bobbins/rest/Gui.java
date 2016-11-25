package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("gui")
public class Gui {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream gui(){
      // Note the leading slash.
      return Gui.class.getResourceAsStream("/index.html");
    }

    @GET
    @Produces("text/css")
    @Path("style")
    public InputStream css(){
        return Gui.class.getResourceAsStream("/style.css");
    }

    //This is a bit dangerous (it'll serve up any file!). //TODO. Make it less so.
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{script}")
    public InputStream script(@PathParam("script") String script){
      String fileName = "/"+script;
      if (!fileName.contains(".")){
        fileName = fileName+".js";
      } 
      InputStream is = Gui.class.getResourceAsStream(fileName);
System.out.println("*** Serving "+fileName);
      return is;
    }
}
