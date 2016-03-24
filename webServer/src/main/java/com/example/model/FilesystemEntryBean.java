package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String name;
  public Integer id;
  public String path;
  public String listActionUrl;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String name, Integer id, String path, String listActionUrl){
    this.name = name;
    this.id = id;
    this.path = path;
    this.listActionUrl = listActionUrl;
  }
}
