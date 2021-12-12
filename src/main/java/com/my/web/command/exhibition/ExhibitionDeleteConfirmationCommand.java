package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExhibitionDeleteConfirmationCommand implements Command {

  /**
   * @return Page to confirm deletion of an exhibition
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    return Jsp.ADMIN_EXHIBITION_DELETE_CONFIRMATION_PAGE;
  }

}
