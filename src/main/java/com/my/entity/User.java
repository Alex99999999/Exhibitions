package com.my.entity;

import java.io.Serializable;
import javax.management.relation.Role;

public class User implements Serializable {

  private long id;
  private String login;
  private String password;
  private UserRole role;

  private User() {
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public static User getInstance() {
    return new User();
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", role=" + role +
        '}';
  }
}
