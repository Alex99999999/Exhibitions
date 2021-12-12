package com.my.web.command.user;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.entity.Booking;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowUserBookingsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowUserBookingsCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    User user = (User)req.getSession().getAttribute(Params.CURRENT_USER);
    long id= user.getId();
    List<Booking> bookingList;
    try {
      bookingList = BookingDao.getInstance().findByUserId(id);
      if (bookingList.isEmpty()) {
        req.getSession().setAttribute(Params.INFO_MESSAGE, "You have no bookings yet!");
        return Jsp.SHOW_USER_BOOKINGS;
      }
    } catch (DBException e) {
      String mes = "Unable to create user account!";
      LOG.warn(mes);
      req.setAttribute(Params.ERROR_MESSAGE, mes);
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.BOOKING_LIST, bookingList);
    return Jsp.SHOW_USER_BOOKINGS;
  }

}
