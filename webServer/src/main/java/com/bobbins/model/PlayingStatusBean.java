package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayingStatusBean {

  public String name;
  public Integer status;
  public String stopActionUrl;
  public String pauseActionUrl;
  public String volumeUpUrl;
  public String volumeDownUrl;
  public String volumeUrl;
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
    this.stopActionUrl = "play/stop";
    this.pauseActionUrl = "play/pause";
    this.volumeUpUrl="play/volume?volume="+(volume+1);
    this.volumeDownUrl="play/volume?volume="+(volume-1);
    this.volumeUrl="play/volume?volume=";
  }
}
