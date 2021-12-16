package com.my.db.dao.bookingStatus;


import com.my.utils.DbUtils;
import com.my.entity.BookingStatus;
import com.my.exception.DBException;
import com.my.utils.Factory;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

class BookingStatusRepo {

  private static BookingStatusRepo instance;
  private static final Logger LOG = Logger.getLogger(BookingStatusRepo.class);
  private static final Factory factory = Factory.getInstance();
  private String errorMes;

  private BookingStatusRepo() {
  }

  public static synchronized BookingStatusRepo getInstance() {
    if (instance == null) {
      instance = new BookingStatusRepo();
    }
    return instance;
  }

  List<BookingStatus> findAllBookingStatuses() throws DBException {
    List<BookingStatus> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.FIND_ALL);
      while (rs.next()) {
        list.add(factory.createBookingStatus(rs));
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

  BookingStatus findStatusById(long id) throws DBException {
    BookingStatus status = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        status = factory.createBookingStatus(rs);
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
    return status;
  }
}
