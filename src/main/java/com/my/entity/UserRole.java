package com.my.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRole implements Serializable {

  private long id;
  private String role;

  private UserRole() {
  }

  public static UserRole getInstance(){
    return new UserRole();
  }
}
