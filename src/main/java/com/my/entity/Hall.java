package com.my.entity;

import java.io.Serializable;

public class Hall implements Serializable {

  private long id;
  private int floor;
  private double floorSpace;
  private int hallNo;
  private HallStatus status;

  private Hall() {
  }

  public static Hall getInstance() {
    return new Hall();
  }

  public long getId() {
    return id;
  }

  public int getFloor() {
    return floor;
  }

  public double getFloorSpace() {
    return floorSpace;
  }

  public int getHallNo() {
    return hallNo;
  }

  public HallStatus getStatus() {
    return status;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public void setFloorSpace(double floorSpace) {
    this.floorSpace = floorSpace;
  }

  public void setHallNo(int hall_no) {
    this.hallNo = hall_no;
  }

  public void setHallStatus(HallStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Hall{" +
        "id=" + id +
        ", floor=" + floor +
        ", floorSpace=" + floorSpace +
        ", hall_no=" + hallNo +
        ", hall_status_id=" + status +
        '}';
  }
}
