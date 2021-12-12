package com.my.entity;

import java.io.Serializable;

public class Currency implements Serializable {

  private long id;
  private String currency;


  private Currency() {
  }

  public static Currency getInstance() {
    return new Currency();
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public long getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return "Currency{" +
        "id=" + id +
        ", currency='" + currency + '\'' +
        '}';
  }
}
