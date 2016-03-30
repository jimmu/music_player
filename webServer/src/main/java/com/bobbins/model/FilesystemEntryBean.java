package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String listActionUrl;
  public String playActionUrl;

  public String artist;
  public String album;
  public String song;
  // possibly delete the properties below

  public String name;
  public Integer id;
  public String path;
  public Boolean isLeaf;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String artist, String album, String song){
    this.artist = artist;
    this.album = album;
    this.song = song;
  }

  public void setListActionUrl(String url){
    this.listActionUrl = url;
  }

  public void setPlayActionUrl(String url){
    this.playActionUrl = url;
  }

  // Possibly delete this
  public FilesystemEntryBean(String name, Integer id, String path, Boolean isLeaf, String listActionUrl, String playActionUrl){
    this.name = name;
    this.id = id;
    this.path = path;
    this.isLeaf = isLeaf;
    this.listActionUrl = listActionUrl;
    this.playActionUrl = playActionUrl;
  }
}
