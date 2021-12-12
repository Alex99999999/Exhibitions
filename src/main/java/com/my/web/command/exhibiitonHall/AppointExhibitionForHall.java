package com.my.web.command.exhibiitonHall;

import com.my.command.Command;
import com.my.db.dao.exhibitionHalls.ExhibitionHallsDao;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AppointExhibitionForHall implements Command {

  private static final Logger LOG = Logger.getLogger(SetHallsForExhibitionCommand.class);
  private static final String ADDRESS = "admin?command=show_appoint_exhibition_for_hall";

  /**
   * Set exhibition for the selected hall. Applies validation for exhibition status. A hall may be
   * assigned only to current or pending exhibition.
   * <p>
   * Also applies validation for hall status. Assigned may be a hall with "free" status only
   *
   * @param req Must contain "exhibition_id" and "free_hall_id" params
   * @return The page request has came from
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    try {
      long exhibitionId = Utils.retrieveId(req.getParameter(Params. EXHIBITION_ID));
      long hallId = Utils.retrieveId(req.getParameter(Params.FREE_HALL_ID));
      ExhibitionHallsDao.getInstance().setHallForExhibition(exhibitionId, hallId);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      req.setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return ADDRESS;
    }
    return ADDRESS;
  }
}
