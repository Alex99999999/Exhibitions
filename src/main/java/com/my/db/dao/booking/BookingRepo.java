package com.my.db.dao.booking;


import com.my.utils.DbUtils;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Booking;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.service.ExhibitionService;
import com.my.utils.Factory;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

class BookingRepo {

  private static BookingRepo instance;
  private static final Logger LOG = Logger.getLogger(BookingRepo.class);
  private static final Factory factory = Factory.getInstance();

  private String errorMes;

  private BookingRepo() {
  }

  public static synchronized BookingRepo getInstance() {
    if (instance == null) {
      instance = new BookingRepo();
    }
    return instance;
  }

  List<Booking> findAllBookings() throws DBException {
    List<Booking> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.FIND_ALL);
      while (rs.next()) {
        list.add(factory.createBooking(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }

  Booking findBookingById(long id) throws DBException {
    Booking booking = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        booking = factory.createBooking(rs);
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return booking;
  }

  /**
   * Transaction Method inserts booking and updates available tickets quantity in exhibition
   *
   * @return Booking instance for the purposes of servlet
   */

  Booking insertBooking(HttpServletRequest req) throws DBException, ServiceException {
    long bookingId = 0;
    int exhibitionId = Integer.parseInt(req.getParameter(Params.EXHIBITION_ID));
    int userId = Integer.parseInt(req.getParameter(Params.USER_ID));
    int ticketsBooked = Integer.parseInt(req.getParameter(Params.TICKETS_BOOKED));
    String status = req.getParameter(Params.BOOKING_STATUS);
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      con.setAutoCommit(false);
      Exhibition ex = ExhibitionDao.getInstance().findById(exhibitionId);
      ex = ExhibitionService.getInstance().setTicketQuantity(ex, ticketsBooked, "decrease");
      ExhibitionDao.getInstance().update(ex);
      stmt = con.prepareStatement(Sql.INSERT, Statement.RETURN_GENERATED_KEYS);
      int k = 0;
      stmt.setInt(++k, ticketsBooked);
      stmt.setInt(++k, exhibitionId);
      stmt.setInt(++k, userId);
      stmt.setString(++k, status);
      stmt.executeUpdate();
      rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        bookingId = rs.getInt(1);
      }
      con.commit();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_CREATE;
      LOG.error(errorMes);
      DbUtils.rollback(con);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return findBookingById(bookingId);
  }

  List<Booking> getByUserId(long id) throws DBException {
    List<Booking> bookings;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_USER_ID);
      stmt.setLong(1, id);
      bookings = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.COLUMN_LABEL_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return bookings;
  }


  /**
   * Transaction Deletes booking and restores tickets quantity in exhibition
   */
  void deleteBooking(long id) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Exhibition ex;
    Booking booking = null;
    try {
      con.setAutoCommit(false);
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        booking = factory.createBooking(rs);
      }
      if (booking == null) {
        throw new DBException("Cannot find booking with id " + id);
      }
      ex = booking.getExhibition();
      ex = ExhibitionService.getInstance()
          .setTicketQuantity(ex, booking.getTicketQty(), "increase");
      ExhibitionDao.getInstance().update(ex);
      stmt = con.prepareStatement(Sql.DELETE_BOOKING_BY_ID);
      stmt.setLong(1, id);
      stmt.executeUpdate();
      con.commit();
    } catch (SQLException | ServiceException e) {
      errorMes = Logs.DELETION_ERROR;
      LOG.error(errorMes);
      DbUtils.rollback(con);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  int findAllBookingCount() throws DBException {
    int total = 0;
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.GET_ALL_BOOKING_COUNT);
      if (rs.next()) {
        total = rs.getInt(1);
      }
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_GET_COUNT;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return total;
  }


  public void update(Booking booking) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(Sql.UPDATE_BOOKING);
      int k = 0;
      pstmt.setInt(++k, booking.getTicketQty());
      pstmt.setString(++k, booking.getStatus().getStatus());
      pstmt.setLong(++k, booking.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_UPDATE;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(pstmt);
      DbUtils.close(con);
    }
  }

  int countByExhibitionIdAndStatus(long id, String booked, String paid) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement ps = null;
    ResultSet rs = null;
    int bookingCount = 0;
    try {
      ps = con.prepareStatement(Sql.FIND_BY_EXHIBITION_ID_AND_BOOKING_STATUS);
      int k = 0;
      ps.setLong(++k, id);
      ps.setString(++k, booked);
      ps.setString(++k, paid);
      rs = ps.executeQuery();
      if (rs.next()) {
        bookingCount = rs.getInt(1);
      }
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_GET_COUNT;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(ps);
      DbUtils.close(con);
    }
    return bookingCount;
  }

  List<Booking> FilterAllByStringLimitOffset(String sql, String filter, int limit, int offset)
      throws DBException {
    List<Booking> bookingList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, filter);
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      bookingList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.SERVER_FILTER_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return bookingList;
  }

  List<Booking> FilterAllByStatusAndLoginLimitOffset(String sql, String login, String status,
      int limit, int offset) throws DBException {
    List<Booking> bookingList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(Sql.FIND_ALL_BY_LOGIN_AND_STATUS_OFFSET_LIMIT_NO_FILTER);
      int k = 0;
      stmt.setString(++k, status);
      stmt.setString(++k, login);
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      bookingList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.COLUMN_LABEL_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return bookingList;
  }

  List<Booking> findAllLimitOffset(String sql, int offset, int limit) throws DBException {
    List<Booking> bookingList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      bookingList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.COLUMN_LABEL_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return bookingList;
  }

  private List<Booking> getList(Connection con, PreparedStatement stmt) throws DBException {
    List<Booking> list = new ArrayList<>();
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createBooking(rs));
      }
    } catch (SQLException e) {
      LOG.error(Logs.NO_BOOKINGS_FOUND);
      throw new DBException(e.getMessage());
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }
}
