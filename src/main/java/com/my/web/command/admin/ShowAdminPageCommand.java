package com.my.web.command.admin;


import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAdminPageCommand implements Command {

  /**
   * @return Admin home page view
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.ADMIN_PAGE;
  }
}
