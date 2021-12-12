package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CancelBookingCommand implements Command {

  private static final Logger LOG = Logger.getLogger(BuyCommand.class);

  /**
   * Implements transaction: on delete restores tickets qty in exhibition
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {

    try {
      long id = Utils.retrieveId(req.getParameter(Params.BOOKING_ID));
      BookingDao.getInstance().delete(id);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return "auth?command=show_user_bookings";
  }

}
