package com.my.web.command.exhibiitonHall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.db.dao.exhibitionHalls.ExhibitionHallsDao;
import com.my.entity.Exhibition;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowHallsForExhibitions implements Command {

  private static final Logger LOG = Logger.getLogger(ShowHallsForExhibitions.class);

  /**
   * Generates two lists of halls - one of those occupied by selected exhibition another one
   * containing unoccupied halls.
   *
   * @param req contains Exhibition instance stored in session
   * @return page to display generated lists of halls attached to the request
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<Hall> occupiedByCurrent;
    List<Hall> free;
    Exhibition ex = (Exhibition) req.getSession().getAttribute(Params.EXHIBITION);
    try {
      Validator.validateNotNull(ex);
      occupiedByCurrent = ExhibitionHallsDao.getInstance().findHallsByExhibitionId(ex.getId());
      free = HallDao.getInstance().findByStatus(Params.FREE);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.EXHIBITION_HALLS, occupiedByCurrent);
    req.setAttribute(Params.FREE_HALLS, free);
    return Jsp.ADMIN_SHOW_HALLS_FOR_EXHIBITIONS;
  }
}
