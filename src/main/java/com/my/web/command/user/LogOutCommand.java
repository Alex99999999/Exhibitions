package com.my.web.command.user;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

  /**
   * Method invalidates current session
   * @return index.jsp
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();

    if (session != null) {
      session.setAttribute(Params.SESSION_ROLE, null);
      session.setAttribute(Params.CURRENT_USER, null);
      session.invalidate();
    }
    return Jsp.START_PAGE;
  }
}
