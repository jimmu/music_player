package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String listActionUrl;
  public String playActionUrl;

  public String artist;
  public String album;
  public String song;

  public boolean isArtist;
  public boolean isAlbum;
  public boolean isSong;

  public String name;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String artist, String album, String song){
    this.artist = artist;
    this.album = album;
    this.song = song;

    isSong = (song != null && !song.trim().isEmpty());
    isAlbum = !isSong && (album != null && !album.trim().isEmpty());
    isArtist = !isSong && !isAlbum && (artist != null && !artist.trim().isEmpty());

    if (isArtist) {
      playActionUrl = null;
      listActionUrl = "list/" + artist;
      name = artist;
    }
    if (isAlbum) {
      playActionUrl = "play/" + artist + "/" + album;
      listActionUrl = "list/" + artist + "/" + album;
      name = album;
    }
    if (isSong) {
      playActionUrl = "play/" + artist + "/" + album + "/" + song;
      listActionUrl = null;
      name = song;
    }
  }

  public String toString(){
    return "[artist: "+artist+", album: "+album+", song: "+song+", name: "+name+"]";
  }
}
