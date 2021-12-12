package com.my.web.command.hall;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAdminCreateHallPage implements Command {

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.SHOW_ADMIN_CREATE_HALL_PAGE;
  }
}
