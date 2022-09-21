package com.bztc.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("website_list")
public class WebsiteList implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主表id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 网站地址
   */
  private String websiteUrl;

  /**
   * 网站名称
   */
  private String websiteName;

  /**
   * 状态
   */
  private String status;

  /**
   * 录入人
   */
  private String inputUser;

  /**
   * 录入时间
   */
  private LocalDateTime inputTime;

  /**
   * 更新人
   */
  private String updateUser;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public LocalDateTime getInputTime() {
    return inputTime;
  }

  public void setInputTime(LocalDateTime inputTime) {
    this.inputTime = inputTime;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public String toString() {
    return "WebsiteList{" +
            "id = " + id +
            ", websiteUrl = " + websiteUrl +
            ", websiteName = " + websiteName +
            ", status = " + status +
            ", inputUser = " + inputUser +
            ", inputTime = " + inputTime +
            ", updateUser = " + updateUser +
            ", updateTime = " + updateTime +
            "}";
  }
}
