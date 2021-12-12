package com.my.web.command.booking;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;

import com.my.entity.Booking;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.service.BookingService;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BuyCommand implements Command {

  private static final Logger LOG = Logger.getLogger(BuyCommand.class);

  /**
   * Creates booking.
   *
   * @param req contains "tickets_booked" request param which must be validated for non-null value to
   *            avoid NPE while parsing into int
   * @return payment confirmation page
   */
  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Booking booking;
    try {
      Validator.validateNotNull(req.getParameter(Params.TICKETS_BOOKED));
      BookingService.getInstance().verifyTicketsQuantity(req);
      booking = BookingDao.getInstance().createBooking(req);
    } catch (DBException | ServiceException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.BOOKING, booking);
    return Jsp.PAYMENT_CONFIRMATION;
  }
}

