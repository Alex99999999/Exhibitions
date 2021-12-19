package com.my.entity;

import java.io.Serializable;
import javax.management.relation.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User implements Serializable {

  private long id;
  private String login;
  private String password;
  private UserRole role;

  private User() {
  }

  public static User getInstance() {
    return new User();
  }

}
