package com.my.entity;

import java.io.Serializable;

public class Booking implements Serializable {

  private long id;
  private int ticketQty;
  private User user;
  private BookingStatus status;
  private Exhibition exhibition;

  public void setStatus(BookingStatus status) {
    this.status = status;
  }

  public Exhibition getExhibition() {
    return exhibition;
  }

  public void setExhibition(Exhibition exhibition) {
    this.exhibition = exhibition;
  }

  private Booking() {
  }

  public static Booking getInstance() {
    return new Booking();
  }

  public long getId() {
    return id;
  }

  public int getTicketQty() {
    return ticketQty;
  }

  public User getUser() {
    return user;
  }

  public BookingStatus getStatus() {
    return status;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTicketQty(int ticketQty) {
    this.ticketQty = ticketQty;
  }

  public void setUser(User user) {
    this.user = user;
  }


  @Override
  public String toString() {
    return "Booking{" +
        "id=" + id +
        ", ticketQty=" + ticketQty +
        ", user=" + user +
        ", status=" + status +
        '}';
  }
}
