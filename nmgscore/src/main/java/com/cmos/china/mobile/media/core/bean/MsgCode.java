package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class MsgCode
{
  private String id;
  private String user;
  private String code;
  private Timestamp usableTime;
  private Timestamp nextTime;

  public MsgCode()
  {
  }

  public MsgCode(String id, String user, String code, Timestamp usableTime, Timestamp nextTime)
  {
    this.id = id;
    this.user = user;
    this.code = code;
    this.usableTime = usableTime;
    this.nextTime = nextTime;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getUser() {
    return this.user;
  }
  public void setUser(String user) {
    this.user = user;
  }
  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public Timestamp getUsableTime() {
    return this.usableTime;
  }
  public void setUsableTime(Timestamp usableTime) {
    this.usableTime = usableTime;
  }
  public Timestamp getNextTime() {
    return this.nextTime;
  }
  public void setNextTime(Timestamp nextTime) {
    this.nextTime = nextTime;
  }
}