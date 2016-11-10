package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayingStatusBean {

  public String name;
  public Integer status;
  public String pauseActionUrl;
  public String volumeUpUrl;
  public String volumeDownUrl;
  public String volumeUrl;
  public Integer volume;

  public PlayingStatusBean(){}

  public PlayingStatusBean(String name, Integer volume){
    this.name = name;
    this.volume = volume;
    this.status = 200;
    this.pauseActionUrl = "pause";
    this.volumeUpUrl="play/volume?volume="+(volume+1);
    this.volumeDownUrl="play/volume?volume="+(volume-1);
    this.volumeUrl="play/volume?volume=";
  }
}
