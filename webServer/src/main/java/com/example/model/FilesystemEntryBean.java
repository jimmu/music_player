package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String name;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String name){
    this.name = name;
  }
}
