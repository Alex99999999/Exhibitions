package com.my.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Status {

  private long id;
  private String status;

  private Status() {
  }

  public static Status getInstance() {
    return new Status();
  }

}
