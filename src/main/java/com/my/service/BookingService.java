package com.my.service;

import com.my.exception.ServiceException;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class BookingService {

  private static final Logger LOG = Logger.getLogger(BookingService.class);
  private static final int MIN_ORDER = 1;
  private static BookingService instance;


  public static BookingService getInstance() {
    if (instance == null) {
      instance = new BookingService();
    }
    return instance;
  }

  private BookingService() {
  }

  public void verifyTicketsQuantity(HttpServletRequest req)
      throws ServiceException {
    int ticketsBooked = Integer.parseInt(req.getParameter(Params.TICKETS_BOOKED));
    if (ticketsBooked < MIN_ORDER) {
      String mes = "Tickets quantity may not be less than 1";
      LOG.warn(mes);
      throw new ServiceException(mes);
    }
  }

}
