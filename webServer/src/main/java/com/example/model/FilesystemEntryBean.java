package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilesystemEntryBean {

  public String name;
  public Integer id;

  public FilesystemEntryBean(){}

  public FilesystemEntryBean(String name, Integer id){
    this.name = name;
    this.id = id;
  }
}
