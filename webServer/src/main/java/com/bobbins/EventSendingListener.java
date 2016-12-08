package com.bobbins;

import com.bobbins.model.PlayingStatusBean;
import com.bobbins.model.PlaylistBean;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class EventSendingListener implements PlayerListener {

    private static final int THROTTLE_LAG = 100;    //Don't ping the clients less than this many ms apart
    private PlayingStatusBean lastSentState = null;
    private PlayingStatusBean latestState;
    private PlaylistBean lastSentPlaylist = null;
    private PlaylistBean latestPlaylist;
    private boolean stop;
    private Thread senderThread;

    public EventSendingListener(final EventOutput eventOutput){
        latestState = null;
        latestPlaylist = null;
        stop = false;
        senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop){
                    if (latestState != null){
                        try {
                            synchronized (this) {
                                final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                                eventBuilder.name("player-state-change");
                                eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
                                eventBuilder.data(PlayingStatusBean.class, latestState);
                                final OutboundEvent event = eventBuilder.build();
                                if (!eventOutput.isClosed()) {
                                    eventOutput.write(event);
                                    lastSentState = latestState;
                                }
                                latestState = null;
                            }
                        } catch (IOException e) {
                            // Oh dear. May as well close the connection then.
                            // The client may re-establish it.
                            // Actually it seems to be better to let the (transient) error pass.
//                            try {
//                                System.out.println("Closing the event output due to an error");
//                                eventOutput.close();
//                            } catch (IOException e1) {
//                                System.out.println("Couldn't close event output. "+e1);
//                            }
                        }
                    }
                    if (latestPlaylist != null){
                        try {
                            synchronized (this) {
                                final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                                eventBuilder.name("playlist-change");
                                eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
                                eventBuilder.data(PlaylistBean.class, latestPlaylist);
                                //eventBuilder.data(listType, latestPlaylist);
                                final OutboundEvent event = eventBuilder.build();
                                if (!eventOutput.isClosed()) {
                                    eventOutput.write(event);
                                    lastSentPlaylist = latestPlaylist;
                                }
                                latestPlaylist = null;
                            }
                        } catch (IOException e) {
                            // Oh dear. May as well close the connection then.
                            // The client may re-establish it.
                            // Actually it seems to be better to let the (transient) error pass.
//                            try {
//                                System.out.println("Closing the event output due to an error");
//                                eventOutput.close();
//                            } catch (IOException e1) {
//                                System.out.println("Couldn't close event output. "+e1);
//                            }
                        }
                    }
                    try {
                        Thread.sleep(THROTTLE_LAG);
                    } catch (InterruptedException e) {
                        //ok
                    }
                }
                // We're done.
                try {
                    System.out.println("Closing the event output because sender thread stopping");
                    eventOutput.close();
                } catch (IOException e) {
                    //ok
                }
            }
        });
        senderThread.setDaemon(true);
    }

    public synchronized void onChange(PlayingStatusBean state){
        if (!senderThread.isAlive()){
            senderThread.start();
        }
        if (state == null){
            stop = true;
        }
        else {
            // Only send data when there has really been a change.
            if (!state.equals(lastSentState)) {
                latestState = state;
            }
        }
    }

    @Override
    public void onPlaylistChange(PlaylistBean playlist){
        if (!senderThread.isAlive()){
            senderThread.start();
        }
	    if (playlist != null){
	        if (!playlist.equals(lastSentPlaylist)) {
                latestPlaylist = playlist;
            }
        }
    }
}
