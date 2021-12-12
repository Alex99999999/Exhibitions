package com.my.db.dao.booking;

import com.my.db.dao.Dao;
import com.my.entity.Booking;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class BookingDao implements Dao {

  private static BookingDao instance;
  private static BookingRepo bookingRepo = BookingRepo.getInstance();

  public static synchronized BookingDao getInstance() {
    if (instance == null) {
      instance = new BookingDao();
    }
    return instance;
  }

  @Override
  public List<Booking> findAll() throws DBException {
    return bookingRepo.findAllBookings();
  }

  @Override
  public Booking findById(long id) throws DBException {
    return bookingRepo.findBookingById(id);

  }

  //To be implemented
  @Override
  public void update(HttpServletRequest req) throws DBException {

  }

  @Override
  public void create(HttpServletRequest req) throws DBException {
  }

  @Override
  public void delete(long id) throws DBException {
    bookingRepo.deleteBooking(id);
  }

  public Booking createBooking(HttpServletRequest req) throws DBException, ServiceException {
    return bookingRepo.insertBooking(req);
  }

  /**
   * @return list of bookings where status is PAID or BOOKED only
   */
  public List<Booking> findByUserId(long id) throws DBException {
    return bookingRepo.getByUserId(id);
  }

  public int getAllBookingsCount() throws DBException {
    return bookingRepo.findAllBookingCount();
  }

  public List<Booking> findAllOffsetLimitNoFilter(int offset, int limit) throws DBException {
    String sql = Sql.FIND_ALL_OFFSET_LIMIT;
    return bookingRepo.findAllLimitOffset(sql, offset, limit);
  }

  public List<Booking> findAllByStatusOffsetLimitNoFilter(int offset, int limit, String status)
      throws DBException {
    String sql = Sql.FIND_ALL_BY_STATUS_OFFSET_LIMIT_NO_FILTER;
    return bookingRepo.FilterAllByStringLimitOffset(sql, status, limit, offset);
  }

  public List<Booking> findAllByUserLoginOffsetLimitNoFilter(int offset, int limit, String login)
      throws DBException {
    String sql = Sql.FIND_ALL_BY_LOGIN_OFFSET_LIMIT_NO_FILTER;
    return bookingRepo.FilterAllByStringLimitOffset(sql, login, limit, offset);
  }

  public List<Booking> findAllByStatusAndUserLoginOffsetLimitNoFilter(int offset, int limit,
      String status, String login) throws DBException {
    String sql = Sql.FIND_ALL_BY_LOGIN_AND_STATUS_OFFSET_LIMIT_NO_FILTER;
    return bookingRepo.FilterAllByStatusAndLoginLimitOffset(sql, login, status, limit, offset);
  }

  public int findCountByExhibitionIdAndStatus(long id, String booked, String paid)
      throws DBException {
    return bookingRepo.countByExhibitionIdAndStatus(id, booked, paid);
  }
}