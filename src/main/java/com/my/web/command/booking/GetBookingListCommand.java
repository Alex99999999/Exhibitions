package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.entity.Booking;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetBookingListCommand implements Command {

  private static final Logger LOG = Logger.getLogger(GetBookingListCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<Booking> list;
    int pageSize;
    int page;
    int bookingCount;
    int offset;
    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      bookingCount = BookingDao.getInstance().getAllBookingsCount();
      offset = Utils.getOffset(pageSize, page);
      Utils.getPagination(req, page, pageSize, bookingCount);
      list = BookingDao.getInstance().findAllOffsetLimitNoFilter(offset, pageSize);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.BOOKING_LIST, list);
    return Jsp.BOOKING_LIST_PAGE;
  }
}
