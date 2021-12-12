package com.my.web.command.user;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginPageCommand implements Command {

  /**
   * @return Sign in page
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.LOGIN_PAGE;
  }
}
