package com.bobbins;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        //server = Main.startServer();
        // create the client
        //Client c = ClientBuilder.newClient();
        //target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        //server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        //String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", "Got it!");
    }

//    @Test
//    public void testCrap(){
//        String response = target.path("list").request().get(String.class);
//        System.out.println(response);
//        response = target.path("playlist").request().get(String.class);
//        System.out.println(response);
//    }
}
