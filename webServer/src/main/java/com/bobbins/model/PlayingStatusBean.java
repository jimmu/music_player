package com.bobbins.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayingStatusBean {

  public String name;
  public Integer status;
  public String stopActionUrl;
  public String nextTrackActionUrl;
  public String previousTrackActionUrl;
  public String pauseActionUrl;
  public String playActionUrl;
  public String volumeUpUrl;
  public String volumeDownUrl;
  public String volumeUrl;
  public String seekPositionUrl;
  public Integer volume;
  public Boolean isPlaying;
  public Integer songLength;
  public Long elapsedTime;

  public PlayingStatusBean(){}

  public PlayingStatusBean(String name, Integer volume, Boolean isPlaying, Integer songLength, Long elapsedTime){
    this.name = name;
    this.volume = volume;
    this.isPlaying = isPlaying;
    this.songLength = songLength;
    this.elapsedTime = elapsedTime;
    this.status = 200;
    this.playActionUrl = "play/play";
    this.stopActionUrl = "play/stop";
    this.pauseActionUrl = "play/pause";
    this.nextTrackActionUrl = "play/next";
    this.previousTrackActionUrl = "play/previous";

    this.volumeUpUrl="play/volume?volume="+(volume+1);
    this.volumeDownUrl="play/volume?volume="+(volume-1);
    this.volumeUrl="play/volume?volume=";
    this.seekPositionUrl="play/seek?position=";
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this)
            .append("name", name)
            .append("volume", volume)
            .append("isPlaying", isPlaying)
            .append("songLength", songLength)
            .append("elapsedTime", elapsedTime)
            .toString();
  }

  @Override
  public int hashCode(){
    return new HashCodeBuilder(17,37)
            .append(name)
            .append(status)
            .append(stopActionUrl)
            .append(nextTrackActionUrl)
            .append(previousTrackActionUrl)
            .append(playActionUrl)
            .append(pauseActionUrl)
            .append(volumeUpUrl)
            .append(volumeDownUrl)
            .append(volumeUrl)
            .append(seekPositionUrl)
            .append(volume)
            .append(isPlaying)
            .append(songLength)
            .append(elapsedTime)
            .toHashCode();
  }

  @Override
  public boolean equals(Object other){
    if (other == null){return false;}
    if (other == this){return true;}
    if (other.getClass() != getClass()){return false;}
    PlayingStatusBean otherBean = (PlayingStatusBean)other;
    return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(name, otherBean.name)
            .append(status, otherBean.status)
            .append(stopActionUrl, otherBean.stopActionUrl)
            .append(nextTrackActionUrl, otherBean.nextTrackActionUrl)
            .append(previousTrackActionUrl, otherBean.previousTrackActionUrl)
            .append(pauseActionUrl, otherBean.pauseActionUrl)
            .append(playActionUrl, otherBean.playActionUrl)
            .append(volumeUpUrl, otherBean.volumeUpUrl)
            .append(volumeDownUrl, otherBean.volumeDownUrl)
            .append(volumeUrl, otherBean.volumeUrl)
            .append(seekPositionUrl, otherBean.seekPositionUrl)
            .append(volume, otherBean.volume)
            .append(isPlaying, otherBean.isPlaying)
            .append(songLength, otherBean.songLength)
            .append(elapsedTime, otherBean.elapsedTime)
            .isEquals();
  }
}
