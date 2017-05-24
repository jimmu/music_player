package com.bobbins;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer server =  GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), createApp());
        // Add a second http server for static content.
        HttpHandler staticContentHandler = new CLStaticHttpHandler(Main.class.getClassLoader());
        server.getServerConfiguration().addHttpHandler(staticContentHandler,"/gui");
        return server;
    }

    public static ResourceConfig createApp(){ 
        // create a resource config that scans for JAX-RS resources and providers
        // in com.bobbins package
        return new ResourceConfig()
               .packages("com.bobbins")
               .register(createMoxyJsonResolver())  //Json stuff.
               .register(new AbstractBinder(){     //DI stuff.
                   @Override
                    protected void configure(){
                       bindFactory(PlayerFactory.class).to(Player.class);
                   }
                });
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl", BASE_URI));
	    try {Thread.sleep(1000*60*60*4);}catch(InterruptedException e){}
        server.stop();
    }
}

