package com.my.entity;

import java.io.Serializable;

public class ExhibitionStatus implements Serializable {

  private long id;
  private String status;

  private ExhibitionStatus() {
  }

  public static ExhibitionStatus getInstance() {
    return new ExhibitionStatus();
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
    return "ExhibitionStatus{" +
        "id=" + id +
        ", status='" + status + '\'' +
        '}';
  }
}
