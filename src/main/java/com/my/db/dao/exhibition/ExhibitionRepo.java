package com.my.db.dao.exhibition;

import com.my.utils.DbUtils;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.exception.DateFormatException;
import com.my.exception.ValidationException;
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

class ExhibitionRepo {

  private static ExhibitionRepo instance;
  private static final Logger LOG = Logger.getLogger(ExhibitionRepo.class);
  private static final Factory factory = Factory.getInstance();
  private String errorMes;

  static synchronized ExhibitionRepo getInstance() {
    if (instance == null) {
      instance = new ExhibitionRepo();
    }
    return instance;
  }

  List<Exhibition> findAllExhibitions() throws DBException {
    List<Exhibition> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.FIND_ALL);
      while (rs.next()) {
        list.add(factory.createExhibition(rs));
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

  Exhibition findByIdentifier(long id) throws DBException {
    Exhibition ex = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        ex = factory.createExhibition(rs);
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
    return ex;
  }

  void update(HttpServletRequest req, String sql) throws DBException {
    String topic = req.getParameter(Params.TOPIC);
    String description = req.getParameter(Params.DESCRIPTION);
    String startDate = req.getParameter(Params.START_DATE);
    String endDate = req.getParameter(Params.END_DATE);
    String startTime = req.getParameter(Params.START_TIME);
    String endTime = req.getParameter(Params.END_TIME);
    double price = Double.parseDouble(req.getParameter(Params.PRICE));
    int ticketsAvailable = Integer.parseInt(req.getParameter(Params.TICKETS_AVAILABLE));
    String status = req.getParameter(Params.STATUS);
    String currency = req.getParameter(Params.CURRENCY);
    long id = Long.parseLong(req.getParameter(Params.EXHIBITION_ID));
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(topic));
      stmt.setString(++k, DbUtils.escapeForPstmt(startDate));
      stmt.setString(++k, DbUtils.escapeForPstmt(endDate));
      stmt.setString(++k, DbUtils.escapeForPstmt(startTime));
      stmt.setString(++k, DbUtils.escapeForPstmt(endTime));
      stmt.setDouble(++k, price);
      stmt.setInt(++k, ticketsAvailable);
      stmt.setString(++k, DbUtils.escapeForPstmt(status));
      stmt.setString(++k, DbUtils.escapeForPstmt(currency));
      stmt.setString(++k, DbUtils.escapeForPstmt(description));
      stmt.setLong(++k, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_UPDATE + topic;
      LOG.error(errorMes, e.getCause());
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }


  void create(HttpServletRequest req, String sql) throws DBException {
    String topic = req.getParameter(Params.TOPIC);
    String description = req.getParameter(Params.DESCRIPTION);
    String startDate = req.getParameter(Params.START_DATE);
    String endDate = req.getParameter(Params.END_DATE);
    String startTime = req.getParameter(Params.START_TIME);
    String endTime = req.getParameter(Params.END_TIME);
    double price = Double.parseDouble(req.getParameter(Params.PRICE));
    int ticketsAvailable = Integer.parseInt(req.getParameter(Params.TICKETS_AVAILABLE));
    String status = req.getParameter(Params.STATUS);
    String currency = req.getParameter(Params.CURRENCY);
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(topic));
      stmt.setString(++k, DbUtils.escapeForPstmt(startDate));
      stmt.setString(++k, DbUtils.escapeForPstmt(endDate));
      stmt.setString(++k, DbUtils.escapeForPstmt(startTime));
      stmt.setString(++k, DbUtils.escapeForPstmt(endTime));
      stmt.setDouble(++k, price);
      stmt.setInt(++k, ticketsAvailable);
      stmt.setString(++k, DbUtils.escapeForPstmt(status));
      stmt.setString(++k, DbUtils.escapeForPstmt(currency));
      stmt.setString(++k, DbUtils.escapeForPstmt(description));
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_UPDATE + topic;
      LOG.error(errorMes, e.getCause());
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  void deleteById(long id) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(Sql.DELETE_BY_ID);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_CREATE;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }


  Exhibition getByTopic(String topic) throws DBException {
    topic = DbUtils.escapeForPstmt(topic);
    Exhibition ex = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_TOPIC);
      stmt.setString(1, "%" + DbUtils.escapeForPstmt(topic) + "%");
      rs = stmt.executeQuery();
      if (rs.next()) {
        ex = factory.createExhibition(rs);
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
    return ex;
  }

  void updateExhibition(Exhibition ex) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(Sql.UPDATE_EXHIBITION);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getTopic()));
      stmt.setString(++k, DbUtils
          .escapeForPstmt(ExhibitionService.getInstance().toDbDateFormat(ex.getStartDate())));
      stmt.setString(++k,
          DbUtils.escapeForPstmt(ExhibitionService.getInstance().toDbDateFormat(ex.getEndDate())));
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getStartTime()));
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getEndTime()));
      stmt.setDouble(++k, ex.getPrice());
      stmt.setInt(++k, ex.getTicketsAvailable());
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getStatus().getStatus()));
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getCurrency().getCurrency()));
      stmt.setString(++k, DbUtils.escapeForPstmt(ex.getDescription()));
      stmt.setLong(++k, ex.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_UPDATE + ex.getTopic();
      LOG.error(errorMes, e.getCause());
      throw new DBException(errorMes);
    } catch (ValidationException | DateFormatException e) {
      LOG.warn(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  List<Exhibition> getExhibitionsWithOffsetAndLimitNoFilter(int offset, int pageSize, String query)
      throws DBException {
    List<Exhibition> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setInt(1, pageSize);
      stmt.setInt(2, offset);
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createExhibition(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.SORTING_ERROR;
      LOG.error(e.getMessage());
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }

  int getCount(String sql) throws DBException {
    int totalExhibitions = 0;
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        totalExhibitions = rs.getInt(1);
      }
    } catch (SQLException e) {
      String errorMes = Logs.UNABLE_TO_GET_COUNT;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return totalExhibitions;
  }

  List<Exhibition> filterByDateOffsetLimit(String userDate, int offset, int limit, String sql)
      throws DBException {
    List<Exhibition> filtered = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(userDate));
      stmt.setString(++k, DbUtils.escapeForPstmt(userDate));
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      rs = stmt.executeQuery();
      while (rs.next()) {
        filtered.add(factory.createExhibition(rs));
      }
    } catch (SQLException e) {
      String errorMes = Logs.SERVER_FILTER_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return filtered;
  }
}

