package com.bobbins.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String name;
  public Integer id;
  public String path;
  public String listActionUrl;
  public String playActionUrl;
  public Boolean isLeaf;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String name, Integer id, String path, Boolean isLeaf, String listActionUrl, String playActionUrl){
    this.name = name;
    this.id = id;
    this.path = path;
    this.isLeaf = isLeaf;
    this.listActionUrl = listActionUrl;
    this.playActionUrl = playActionUrl;
  }
}
