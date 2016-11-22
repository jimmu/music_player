package com.bobbins;

import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class EventSendingListener implements PlayerListener {
    private EventOutput eventOutput;

    public EventSendingListener(EventOutput eventOutput){
        this.eventOutput = eventOutput;
    }

    public void onChange(PlayingStatusBean state){
        try {
            final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
            eventBuilder.name("player-state-change");
            eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
            eventBuilder.data(PlayingStatusBean.class, state);
            final OutboundEvent event = eventBuilder.build();
            if (eventOutput.isClosed()){    //TODO. Work out if/when we should close this.
                System.out.println("Oh - the EventOutput object is closed!");
                System.out.println("*NOT* writing: "+event);
            }
            else {
                eventOutput.write(event);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error when writing the event.", e);
        } //finally {
        //try {
        //eventOutput.close();
        //} catch (IOException ioClose) {
        //throw new RuntimeException(
        //"Error when closing the event output.", ioClose);
        //}
        //}
    }
}
