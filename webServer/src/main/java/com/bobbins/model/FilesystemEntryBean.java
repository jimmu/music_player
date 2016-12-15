package com.bobbins.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public static final String LIST_BASE_URL = "list";
  public static final String PLAY_BASE_URL = "play";

  public String listActionUrl;
  public String playActionUrl;

  public String artist;
  public String album;
  public String song;

  public boolean isArtist;
  public boolean isAlbum;
  public boolean isSong;

  public String name;

  public FilesystemEntryBean(){}  //Zero arg constructor needed for Json stuff to work.

  public FilesystemEntryBean(String artist, String album, String song){
    this.artist = artist;
    this.album = album;
    this.song = song;

    isSong = (song != null && !song.trim().isEmpty());
    isAlbum = !isSong && (album != null && !album.trim().isEmpty());
    isArtist = !isSong && !isAlbum && (artist != null && !artist.trim().isEmpty());

    if (isArtist) {
      playActionUrl = null;
      listActionUrl = LIST_BASE_URL + "/" + artist;
      name = artist;
    }
    if (isAlbum) {
      playActionUrl = PLAY_BASE_URL + "/" + artist + "/" + album;
      listActionUrl = LIST_BASE_URL + "/" +artist + "/" + album;
      name = album;
    }
    if (isSong) {
      playActionUrl = PLAY_BASE_URL + "/" + artist + "/" + album + "/" + song;
      listActionUrl = null;
      name = song;
    }
  }

  public String toString(){
    return "[artist: "+artist+", album: "+album+", song: "+song+", name: "+name+"]";
  }

  @Override
  public boolean equals(Object other){
    if (other == null){return false;}
    if (other == this){return true;}
    if (other.getClass() != getClass()){return false;}
    FilesystemEntryBean otherBean = (FilesystemEntryBean) other;
    return new EqualsBuilder()
            .append(artist, otherBean.artist)
            .append(album, otherBean.album)
            .append(song, otherBean.song)
            .isEquals();
  }

  @Override
  public int hashCode(){
    return new HashCodeBuilder(17,37)
            .append(artist)
            .append(album)
            .append(song)
            .toHashCode();
  }


}
