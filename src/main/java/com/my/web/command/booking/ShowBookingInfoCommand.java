package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.CommandException;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowBookingInfoCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowBookingInfoCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Exhibition ex;
    try {
      long id = Utils.retrieveId(req.getParameter(Params.EXHIBITION_ID));
      ex = ExhibitionDao.getInstance().findById(id);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITION, ex);
    return Jsp.SHOW_BOOKING_INFO;
  }
}
