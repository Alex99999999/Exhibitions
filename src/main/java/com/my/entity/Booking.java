package com.my.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Booking implements Serializable {

  private long id;
  private int ticketQty;
  private User user;
  private Exhibition exhibition;
  private Status status;

  private Booking() {
  }

  public static Booking getInstance() {
    return new Booking();
  }
}
