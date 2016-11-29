package com.bobbins;

import com.bobbins.model.PlayingStatusBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class EventSendingListener implements PlayerListener {

    private static final int THROTTLE_LAG = 100;    //Don't ping the clients less than this many ms apart
    private PlayingStatusBean latestState;
    private boolean somethingToSend;
    private boolean stop;

    public EventSendingListener(final EventOutput eventOutput){
        somethingToSend = false;
        stop = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop){
                    if (somethingToSend){
                        try {
                            synchronized (this) {
                                final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                                eventBuilder.name("player-state-change");
                                eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
                                eventBuilder.data(PlayingStatusBean.class, latestState);
                                final OutboundEvent event = eventBuilder.build();
                                if (eventOutput.isClosed()) {    //TODO. Work out if/when we should close this.
                                    System.out.println("Oh - the EventOutput object is closed!");
                                    System.out.println("*NOT* writing: " + event);
                                } else {
                                    eventOutput.write(event);
                                    somethingToSend = false;
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(
                                    "Error when writing the event.", e);
                        }
                    }
                    try {
                        Thread.sleep(THROTTLE_LAG);
                    } catch (InterruptedException e) {
                        //ok
                    }
                }
            }
        }).start();
    }

    public synchronized void onChange(PlayingStatusBean state){
        latestState = state;
        somethingToSend = true;
    }
}
