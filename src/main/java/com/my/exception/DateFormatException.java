package com.my.exception;

public class DateFormatException extends Throwable {

  public DateFormatException(String mes, Throwable cause){
    super (mes, cause);
  }

  public DateFormatException(String mes){
    super (mes);
  }


}
