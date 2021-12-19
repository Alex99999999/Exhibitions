package com.my.utils;

import com.my.db.dao.bookingStatus.BookingStatusDao;
import com.my.db.dao.currency.CurrencyDao;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.exhibitionStatus.ExhibitionStatusDao;
import com.my.db.dao.hallStatus.HallStatusDao;
import com.my.db.dao.role.RoleDao;
import com.my.db.dao.user.UserDao;
import com.my.entity.Booking;
import com.my.entity.Currency;
import com.my.entity.Exhibition;
import com.my.entity.Hall;
import com.my.entity.Status;
import com.my.entity.User;
import com.my.entity.UserRole;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.service.ExhibitionService;
import com.my.utils.constants.Columns;
import com.my.utils.constants.Logs;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class Factory {

  private static final Logger LOG = Logger.getLogger(Factory.class);
  private static final String ERROR_MES = Logs.COLUMN_LABEL_ERROR;
  private static ExhibitionService service = ExhibitionService.getInstance();
  private static Factory factory;

  private Factory() {
  }

  public static synchronized Factory getInstance() {
    if (factory == null) {
      factory = new Factory();
    }
    return factory;
  }

  public UserRole createUserRole(ResultSet rs) throws DBException {
    UserRole role = UserRole.getInstance();
    try {
      role.setId(rs.getLong(Columns.ID));
      role.setRole(rs.getString(Columns.ROLE));
    } catch (SQLException e) {
      LOG.error(ERROR_MES, e);
      throw new DBException(ERROR_MES, e);
    }
    return role;
  }

  public User createUser(ResultSet rs) throws DBException {
    User user = User.getInstance();
    try {
      user.setRole(RoleDao.getInstance().findById(rs.getLong(Columns.USER_ROLE_ID)));
      user.setId(rs.getLong(Columns.ID));
      user.setPassword(rs.getString(Columns.PASSWORD));
      user.setLogin(rs.getString(Columns.LOGIN));
    } catch (SQLException e) {
      LOG.error(ERROR_MES, e);
      throw new DBException(ERROR_MES, e);
    }
    return user;
  }

  public Exhibition createExhibition(ResultSet rs) throws DBException {
    Exhibition exhibition = Exhibition.getInstance();
    try {
      exhibition.setId(rs.getLong(Columns.ID));
      String startDate = service.dateDisplayFormat(rs.getString(Columns.START_DATE));
      String endDate = service.dateDisplayFormat(rs.getString(Columns.END_DATE));
      String startTime = service.timeDisplayFormat(rs.getString(Columns.START_TIME));
      String endTime = service.timeDisplayFormat(rs.getString(Columns.END_TIME));
      exhibition.setStartDate(startDate);
      exhibition.setEndDate(endDate);
      exhibition.setStartTime(startTime);
      exhibition.setEndTime(endTime);
      exhibition.setPrice(rs.getDouble(Columns.PRICE));
      exhibition.setTicketsAvailable(rs.getInt(Columns.TICKETS_AVAILABLE));
      exhibition.setTopic(rs.getString(Columns.TOPIC));
      exhibition.setCurrency(CurrencyDao.getInstance().findById(rs.getLong(Columns.CURRENCY_ID)));
      exhibition
          .setStatus(ExhibitionStatusDao.getInstance().findById(rs.getLong(Columns.STATUS_ID)));
    } catch (SQLException | ValidationException e) {
      LOG.error(ERROR_MES, e);
      throw new DBException(ERROR_MES, e);

    }
    return exhibition;
  }

  public Currency createCurrency(ResultSet rs) throws DBException {
    Currency currency = Currency.getInstance();
    try {
      currency.setId(rs.getLong(Columns.ID));
      currency.setCurrency(rs.getString(Columns.CURRENCY));
    } catch (SQLException e) {
      LOG.warn(ERROR_MES, e);
      throw new DBException(ERROR_MES);
    }
    return currency;
  }

  public Hall createHall(ResultSet rs) throws DBException {
    Hall hall = Hall.getInstance();
    try {
      hall.setId(rs.getLong(Columns.ID));
      hall.setFloor(rs.getInt(Columns.FLOOR));
      hall.setHallNo(rs.getInt(Columns.HALL_NO));
      hall.setFloorSpace(rs.getDouble(Columns.FLOOR_SPACE));
      hall.setStatus(HallStatusDao.getInstance().findById(rs.getLong(Columns.STATUS_ID)));
    } catch (SQLException e) {
      LOG.warn(ERROR_MES, e);
      throw new DBException(ERROR_MES);
    }
    return hall;
  }

  public Booking createBooking(ResultSet rs) throws DBException {
    Booking booking = Booking.getInstance();
    try {
      booking.setId(rs.getLong(Columns.ID));
      booking.setTicketQty(rs.getInt(Columns.TICKET_QTY));
      booking.setUser(UserDao.getInstance().findById(rs.getLong(Columns.USER_ID)));
      booking.setStatus(BookingStatusDao.getInstance().findById(rs.getLong(Columns.STATUS_ID)));
      booking.setExhibition(ExhibitionDao.getInstance().findById(rs.getInt(Columns.EXHIBITION_ID)));
    } catch (SQLException e) {
      LOG.warn(ERROR_MES, e);
      throw new DBException(ERROR_MES);
    }
    return booking;
  }

  public Status createStatus(ResultSet rs) throws DBException {
    Status status = Status.getInstance();
    try {
      status.setId(rs.getLong(Columns.ID));
      status.setStatus(rs.getString(Columns.STATUS));
    } catch (SQLException e) {
      LOG.warn(ERROR_MES, e);
      throw new DBException(ERROR_MES);
    }
    return status;
  }
}
