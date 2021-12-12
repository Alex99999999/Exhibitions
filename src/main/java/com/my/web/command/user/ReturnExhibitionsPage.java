package com.my.web.command.user;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReturnExhibitionsPage implements Command {

  /**
   * @return View for current exhibitions list. Available for users and authorized users
   */
  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
  }
}
