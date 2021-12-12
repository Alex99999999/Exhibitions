package com.my.web.command.user;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowUserAccountCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowUserBookingsCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.SHOW_USER_ACCOUNT;
  }
}
