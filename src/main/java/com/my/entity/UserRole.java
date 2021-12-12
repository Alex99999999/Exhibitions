package com.my.entity;

import java.io.Serializable;

public class UserRole implements Serializable {

  private long id;
  private String role;

  private UserRole() {
  }

  public static UserRole getInstance() {
    return new UserRole();
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public long getId() {
    return id;
  }

  public String getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "UserRole{" +
        "id=" + id +
        ", role='" + role + '\'' +
        '}';
  }
}
