package com.my.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Exhibition implements Serializable {

  private long id;
  private String topic;
  private String description;
  private String startDate;
  private String endDate;
  private String startTime;
  private String endTime;
  private double price;
  private int ticketsAvailable;
  private Status status;
  private Currency currency;

  private Exhibition() {
  }

  public static Exhibition getInstance() {
    return new Exhibition();
  }

}
