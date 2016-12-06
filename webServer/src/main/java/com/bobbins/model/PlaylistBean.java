package com.bobbins.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37)
                .append(trackList)
                .append(trackCount)
                .append(currentTrackNumber)
                .append(currentTrackName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other){
        if (other == null){return false;}
        if (other == this){return true;}
        if (other.getClass() != getClass()){return false;}
        PlaylistBean otherBean = (PlaylistBean)other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(trackList, otherBean.trackList)
                .append(trackCount, otherBean.trackCount)
                .append(currentTrackNumber, otherBean.currentTrackNumber)
                .append(currentTrackName, otherBean.currentTrackName)
                .isEquals();
    }

}
