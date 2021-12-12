package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.entity.Booking;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class FilterBookingsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(FilterBookingsCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    BookingDao bookingDao = BookingDao.getInstance();
    String userLogin = req.getParameter(Params.USER_LOGIN);
    String bookingStatus = req.getParameter(Params.BOOKING_STATUS);
    List<Booking> list;
    int pageSize = Integer.parseInt(req.getParameter(Params.PAGE_SIZE));
    int page = Integer.parseInt(req.getParameter(Params.PAGE));
    int bookingCount;
    int offset;
    try {
      bookingCount = bookingDao.getAllBookingsCount();
      offset = Utils.getOffset(pageSize, page);
      if (userLogin.isEmpty()) {
        list = bookingDao.findAllByStatusOffsetLimitNoFilter(offset, pageSize, bookingStatus);
      } else if (bookingStatus == null) {
        list = bookingDao.findAllByUserLoginOffsetLimitNoFilter(offset, pageSize, userLogin);
      } else {
        list = bookingDao
            .findAllByStatusAndUserLoginOffsetLimitNoFilter(offset, pageSize, bookingStatus, userLogin);
      }
      Utils.getPagination(req, page, pageSize, bookingCount);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    if (list.isEmpty()) {
      req.getSession().setAttribute(Params.INFO_MESSAGE, "No booking found with requested parameters");
    }
    req.getSession().setAttribute(Params.BOOKING_LIST, list);
    return Jsp.ADMIN_BOOKINGS_PAGE;
  }
}

