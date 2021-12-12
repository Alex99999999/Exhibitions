package com.my.service;

import com.my.entity.Exhibition;
import com.my.exception.DateFormatException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Logs;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ExhibitionService {

  private static final Logger LOG = Logger.getLogger(ExhibitionService.class);
  private static ExhibitionService instance;
  private String errorMes;
  private static final String INVALID_DATE_RANGE = "Invalid date range";

  public static ExhibitionService getInstance() {
    if (instance == null) {
      instance = new ExhibitionService();
    }
    return instance;
  }

  private ExhibitionService() {
  }

  public void validateInput(HttpServletRequest req) throws ValidationException, ServiceException {
    Validator.validateRequestParamsNotNull(req);
    verifyBeginningBeforeEnd(req);
    validateDateAndStatus(req);
    isPositive(req);
  }

  private void isPositive(HttpServletRequest req) throws ValidationException {
    String inputTickets = req.getParameter(Params.TICKETS_AVAILABLE);
    String inputPrice = req.getParameter(Params.PRICE);
    Validator.validateNotNull(inputTickets);
    Validator.validateNotNull(inputPrice);
    int tickets = Integer.parseInt(inputTickets);
    double price = Double.parseDouble(inputPrice);
    if (tickets < 0 || price < 0) {
      throw new ValidationException("Negative value not allowed");
    }
  }

  private void validateDateAndStatus(HttpServletRequest req)
      throws ValidationException, ServiceException {
    Date startDate = toDate(req.getParameter(Params.START_DATE));
    Date endDate = toDate(req.getParameter(Params.END_DATE));
    String status = req.getParameter(Params.STATUS);
    Date now = new Date();
    boolean isCurrent = status.equalsIgnoreCase(Params.CURRENT);
    boolean isPending = status.equalsIgnoreCase(Params.PENDING);

    if (isCurrent && endDate.before(now) || startDate.after(now)) {
      LOG.warn(INVALID_DATE_RANGE);
      throw new ValidationException(INVALID_DATE_RANGE);
    }

    if (isPending && startDate.before(now)) {
      LOG.warn(INVALID_DATE_RANGE);
      throw new ValidationException(INVALID_DATE_RANGE);
    }
  }

  public void verifyBeginningBeforeEnd(HttpServletRequest req) throws ServiceException {
    String startDate = req.getParameter(Params.START_DATE);
    String endDate = req.getParameter(Params.END_DATE);
    String startTime = req.getParameter(Params.START_TIME);
    String endTime = req.getParameter(Params.END_TIME);

    boolean date = validateDates(startDate, endDate);
    if (!date) {
      errorMes = INVALID_DATE_RANGE;
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }

    boolean time = validateTime(startTime, endTime);
    if (!time) {
      errorMes = "Invalid time range";
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }
  }

  private boolean validateDates(String startDate, String endDate) throws ServiceException {
    Date start = toDate(startDate);
    Date end = toDate(endDate);
    return end.after(start);
  }

  private boolean validateTime(String startTime, String endTime) throws ServiceException {
    Date start = toTime(startTime);
    Date end = toTime(endTime);
    return end.after(start);
  }

  private Date toDate(String date) throws ServiceException {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      errorMes = "Date parse exception " + date;
      LOG.error(errorMes);
      throw new ServiceException(errorMes, e.getCause());
    }
  }

  private Date toTime(String time) throws ServiceException {
    try {
      return new SimpleDateFormat("hh:mm").parse(time);
    } catch (ParseException e) {
      errorMes = "Time parse exception " + time;
      LOG.error(errorMes);
      throw new ServiceException(errorMes, e.getCause());
    }
  }

  public Exhibition setTicketQuantity(Exhibition inst, int booked, String action)
      throws ServiceException {
    int tickets = inst.getTicketsAvailable();
    int newQty;
    if (action.equalsIgnoreCase("increase")) {
      newQty = tickets + booked;
    } else {
      newQty = tickets - booked;
      if (newQty < 0) {
        throw new ServiceException("Impossible to book " + booked + " tickets");
      }
    }
    inst.setTicketsAvailable(newQty);
    return inst;
  }

  public String DateDisplayFormat(String inputDate) throws ValidationException {
    Validator.validateNotNull(inputDate);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(inputDate, formatter);
    int year = localDate.getYear();
    String day = addZeroAhead(localDate.getDayOfMonth());
    String monthName = localDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.UK);

    return String.format("%s %s, %d", monthName, day, year);
  }

  public String TimeDisplayFormat(String inputTime) throws ValidationException {
    Validator.validateNotNull(inputTime);
    LocalTime localTime = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
    int hour = localTime.getHour();
    String outputHour = addZeroAhead(hour);
    int minute = localTime.getMinute();
    String outputMinute = addZeroAhead(minute);

    return String.format("%s:%s", outputHour, outputMinute);
  }

  private String addZeroAhead(int input) {
    if (input >= 10) {
      return String.valueOf(input);
    }
    return "0" + input;
  }

  public String toDbDateFormat(String inputDate) throws ValidationException, DateFormatException {
    Validator.validateNotNull(inputDate);
    ifMatchesPattern(inputDate);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
    LocalDate date = LocalDate.parse(inputDate, formatter);
    return date.toString();
  }

  private void ifMatchesPattern(String date) throws DateFormatException {
    String regex = "(\\w{3})\\s(\\d){2},\\s(\\d){4}";
    if (!date.matches(regex)) {
      throw new DateFormatException(Logs.WRONG_DATE_FORMAT);
    }
  }

}
