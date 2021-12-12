package com.my.exception;

public class CommandException extends Throwable {

  public CommandException(String mes) {
    super(mes);
  }

  public CommandException(String mes, Throwable cause) {
    super(mes, cause);
  }

}
