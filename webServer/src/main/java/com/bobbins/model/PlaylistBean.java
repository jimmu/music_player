package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PlaylistBean {

    public List<FilesystemEntryBean> trackList;
    public Integer trackCount;
    public Integer currentTrackNumber;
    public String currentTrackName;

    public PlaylistBean(){} // Got to have this for the json marshalling to work!

    public PlaylistBean(List<FilesystemEntryBean> tracks, String currentTrack){
        if (tracks != null) {
            trackList = new ArrayList<>(tracks);
            trackCount = trackList.size();
            if (currentTrack != null && !currentTrack.trim().isEmpty()) {
                currentTrackName = currentTrack;
                boolean found = false;
                for (int i=0; i<tracks.size() && !found; i++){
                    if (currentTrack.equals(trackList.get(i).name)){
                        currentTrackNumber = i;
                        found = true;
                    }
                }
            }
        }
    }
}
