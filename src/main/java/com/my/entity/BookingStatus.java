package com.my.entity;

import java.io.Serializable;

public class BookingStatus implements Serializable {

  private long id;
  private String status;

  private BookingStatus() {
  }

  public static BookingStatus getInstance() {
    return new BookingStatus();
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
    return "BookingStatus{" +
        "id=" + id +
        ", status='" + status + '\'' +
        '}';
  }
}
