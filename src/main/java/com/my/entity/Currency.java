package com.my.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Currency implements Serializable {

  private long id;
  private String currency;


  private Currency() {
  }

  public static Currency getInstance() {
    return new Currency();
  }

}
