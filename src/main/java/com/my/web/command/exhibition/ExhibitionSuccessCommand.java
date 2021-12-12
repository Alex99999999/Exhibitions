package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExhibitionSuccessCommand implements Command {

  /**
   * @return Page with info about successful exhibition deletion
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.EXHIBITION_SUCCESS_PAGE;
  }
}
