package com.my.exception;

public class ServiceException extends Throwable {

  public ServiceException(String mes, Throwable cause) {
    super(mes, cause);
  }

  public ServiceException(String mes) {
    super(mes);
  }

}
