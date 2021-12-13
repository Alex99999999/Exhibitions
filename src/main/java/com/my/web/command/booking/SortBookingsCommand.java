package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.entity.Booking;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.sortBookings.Sortable;
import com.my.utils.sortBookings.SortingContainer;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SortBookingsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(SortBookingsCommand.class);
  private static final BookingDao BOOKING_DAO = BookingDao.getInstance();

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Sortable sortable;
    List<Booking> list;
    List<Booking> sorted;
    int bookingCount;
    int page;
    int pageSize;
    int offset;

    String sortingOption = req.getParameter(Params.SORTING_OPTION);

    if (sortingOption == null) {
      return Jsp.ADMIN_BOOKINGS_PAGE;
    }

    try {
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      offset = Utils.getOffset(pageSize, page);
      bookingCount = BOOKING_DAO.getAllBookingsCount();

      if (bookingCount < 2) {
        return Jsp.ADMIN_BOOKINGS_PAGE;
      }

      list = BOOKING_DAO.findAll();

      sortable = SortingContainer.getSortingOption(sortingOption);
      sorted = sortable.sort(list);

      if (sorted.size() < offset) {
        sorted = sorted.subList(0, sorted.size());
      } else if (sorted.size() < offset + pageSize) {
        sorted = sorted.subList(offset, offset + (sorted.size() - offset));
      } else {
        sorted = sorted.subList(offset, offset + pageSize);
      }

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

    req.getSession().setAttribute(Params.BOOKING_LIST, sorted);
    req.getSession().setAttribute(Params.SORTING_OPTION, sortingOption);
    return Jsp.ADMIN_BOOKINGS_PAGE;
  }
}
