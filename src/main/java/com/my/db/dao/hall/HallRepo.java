package com.my.db.dao.hall;

import com.my.utils.DbUtils;
import com.my.entity.Hall;
import com.my.exception.DBException;
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

public class HallRepo {

  private static HallRepo instance;
  private String errorMes;
  private static final Logger LOG = Logger.getLogger(HallRepo.class);
  private static final Factory factory = Factory.getInstance();
  private static final String DEFAULT_STATUS = Params.FREE;

  public static synchronized HallRepo getInstance() {
    if (instance == null) {
      instance = new HallRepo();
    }
    return instance;
  }

  List<Hall> findAllHalls() throws DBException {
    Connection con = DbUtils.getCon();
    List<Hall> list;
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(Sql.FIND_ALL);
      list = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return list;
  }

  Hall findByIdentifier(long id) throws DBException {
    Hall hall = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        hall = factory.createHall(rs);
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
    return hall;
  }

  List<Hall> getByStatus(String status) throws DBException {
    List<Hall> list;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_STATUS);
      stmt.setString(1, DbUtils.escapeForPstmt(status));
      list = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return list;
  }

  void updateInstance(Hall hall, String status) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(Sql.UPDATE_HALL);
      int k = 0;
      stmt.setInt(++k, hall.getFloor());
      stmt.setDouble(++k, hall.getFloorSpace());
      stmt.setInt(++k, hall.getHallNo());
      stmt.setString(++k, DbUtils.escapeForPstmt(status));
      stmt.setLong(++k, hall.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_UPDATE;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  int getCount() throws DBException {
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    int count = 0;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.GET_COUNT);
      if (rs.next()) {
        count = rs.getInt(1);
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
    return count;
  }

  List<Hall> getAllLimitOffset(int offset, int limit) throws DBException {
    List<Hall> list;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(Sql.GET_ALL_LIMIT_OFFSET);
      stmt.setInt(1, limit);
      stmt.setInt(2, offset);
      list = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return list;
  }

  List<Hall> getHallsSetInt(int offset, int limit, int space, String sql) throws DBException {
    List<Hall> hallList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setInt(++k, space);
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      hallList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return hallList;
  }

  List<Hall> getHallsSetString(int offset, int limit, String status, String sql)
      throws DBException {
    List<Hall> hallList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(status));
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      hallList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return hallList;
  }

  List<Hall> getHallsByStatusAndSpaceLimitOffset(int offset, int limit, String status, int space,
      String sql)
      throws DBException {
    List<Hall> hallList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(status));
      stmt.setInt(++k, space);
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      hallList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return hallList;
  }

  void createHall(HttpServletRequest req) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement(Sql.INSERT_HALL);
      int k = 0;
      ps.setInt(++k, Integer.parseInt(req.getParameter(Params.HALL_FLOOR)));
      ps.setDouble(++k, Double.parseDouble(req.getParameter(Params.HALL_FLOOR_SPACE)));
      ps.setInt(++k, Integer.parseInt(req.getParameter(Params.HALL_NO)));
      ps.setString(++k, DbUtils.escapeForPstmt(DEFAULT_STATUS));
      ps.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_CREATE;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(ps);
      DbUtils.close(con);
    }
  }

  List<Hall> findAllLimitOffsetOrder(String sql, int limit, int offset) throws DBException {
    List<Hall> hallList;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(sql);
      int k = 0;
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      hallList = getList(con, stmt);
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return hallList;
  }

  Hall findHallByNo(int num) throws DBException {
    Hall hall = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_HALL_BY_NUMBER);
      stmt.setInt(1, num);
      rs = stmt.executeQuery();
      if (rs.next()) {
        hall = factory.createHall(rs);
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
    return hall;
  }

  private List<Hall> getList(Connection con, PreparedStatement stmt) throws DBException {
    List<Hall> list = new ArrayList<>();
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createHall(rs));
      }
    } catch (SQLException e) {
      LOG.error(Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      throw new DBException(e.getMessage());
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }
}