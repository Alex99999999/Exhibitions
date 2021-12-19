package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.db.dao.bookingStatus.BookingStatusDao;
import com.my.entity.Booking;
import com.my.entity.Status;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowAdminBookingsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowAdminBookingsCommand.class);
  private static final BookingDao BOOKING_DAO = BookingDao.getInstance();

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<Booking> list;
    List<Status> statusList;
    int pageSize;
    int page;
    int bookingCount;
    int offset;

    try {
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      statusList = BookingStatusDao.getInstance().findAll();
      offset = Utils.getOffset(pageSize, page);
      bookingCount = BOOKING_DAO.getAllBookingsCount();
      list = BOOKING_DAO.findAllOffsetLimitNoFilter(offset, pageSize);
      Utils.getPagination(req, page, pageSize, bookingCount);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return Jsp.ADMIN_BOOKINGS_PAGE;
    }
    req.getSession().setAttribute(Params.BOOKING_STATUS_LIST, statusList);
    req.getSession().setAttribute(Params.BOOKING_LIST, list);

    return Jsp.ADMIN_BOOKINGS_PAGE;
  }
}
