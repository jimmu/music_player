package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayingStatusBean {

  public String path;
  public Integer status;
  public String pauseActionUrl;

  public PlayingStatusBean(){}

  public PlayingStatusBean(String path){
    this.path = path;
    this.status = 200;
    this.pauseActionUrl = "pause";
  }
}
