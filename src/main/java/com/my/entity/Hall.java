package com.my.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Hall implements Serializable {

  private long id;
  private int floor;
  private double floorSpace;
  private int hallNo;
  private Status status;

  private Hall() {
  }

  public static Hall getInstance() {
    return new Hall();
  }


}
