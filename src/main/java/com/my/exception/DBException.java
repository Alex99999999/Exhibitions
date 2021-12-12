package com.my.exception;

public class DBException extends Throwable {

  public DBException(String mes, Throwable cause){
    super (mes, cause);
  }

  public DBException(String mes){
    super (mes);
  }

}
