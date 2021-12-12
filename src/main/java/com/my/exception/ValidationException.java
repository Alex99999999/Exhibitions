package com.my.exception;

public class ValidationException extends Throwable {

  public ValidationException(String mes, Throwable cause) {
    super(mes, cause);
  }

  public ValidationException(String mes) {
    super(mes);
  }

}
