package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetCurrentExhibitionsListCommand implements Command {

  private static Logger LOG = Logger.getLogger(GetCurrentExhibitionsListCommand.class);

  /**
   * Implements pagination
   *
   * @return Paginated list of current exhibitions to be displayed for "users" and "authorized users"
   * Error page in case of major exceptions
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    try {
      Utils.getCurrentExhibitionList(req);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
  }
}
