package com.my.entity;

import java.io.Serializable;

public class HallStatus implements Serializable {

  private long id;
  private String status;

  private HallStatus() {
  }

  public static HallStatus getInstance() {
    return new HallStatus();
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "HallStatus{" +
        "id=" + id +
        ", status='" + status + '\'' +
        '}';
  }
}
