package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.CommandException;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowBookingInfoCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowBookingInfoCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Exhibition ex;
    try {
      Utils.validateNotNull(req.getParameter("id"));
      long id = Long.parseLong(req.getParameter("id"));
      ex = ExhibitionDao.getInstance().findById(id);
    } catch (CommandException | DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute("errorMessage", e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute("exhibition", ex);
    return Jsp.SHOW_BOOKING_INFO;
  }
}
