package com.bztc.entity;


public class WebsiteList {

  private long id;
  private String websiteUrl;
  private String websiteName;
  private String status;
  private String inputUser;
  private java.sql.Timestamp inputTime;
  private String updateUser;
  private java.sql.Timestamp updateTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }


  public String getWebsiteName() {
    return websiteName;
  }

  public void setWebsiteName(String websiteName) {
    this.websiteName = websiteName;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getInputUser() {
    return inputUser;
  }

  public void setInputUser(String inputUser) {
    this.inputUser = inputUser;
  }


  public java.sql.Timestamp getInputTime() {
    return inputTime;
  }

  public void setInputTime(java.sql.Timestamp inputTime) {
    this.inputTime = inputTime;
  }


  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }

}
