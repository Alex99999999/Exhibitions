package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowAppointExhibitionForHall implements Command {

  private static Logger LOG = Logger.getLogger(ShowAppointExhibitionForHall.class);

  /**
   * Retrieves info on the selected hall along with the list of current exhibitions
   *
   * @return Page displaying selected hall and list of current exhibitions this hall may be
   * appointed to.
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Hall hall = null;
    long hallId;
    try {
      Utils.getCurrentExhibitionList(req);
      hallId = Utils.retrieveId(req.getParameter(Params.HALL_ID));
      hall = HallDao.getInstance().findById(hallId);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      e.printStackTrace();
    }
    req.getSession().setAttribute(Params.HALL, hall);
    return Jsp.ADMIN_APPOINT_EXHIBITION_PAGE;
  }
}
