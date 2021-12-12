package com.my.db.dao.hallStatus;

import com.my.utils.DbUtils;
import com.my.entity.HallStatus;
import com.my.exception.DBException;
import com.my.utils.Factory;
import com.my.utils.constants.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

class HallStatusRepo {

  private static HallStatusRepo instance;
  private static final Logger LOG = Logger.getLogger(HallStatusRepo.class);
  private static final Factory factory = Factory.getInstance();
  private String errorMes;

  public static synchronized HallStatusRepo getInstance() {
    if (instance == null) {
      instance = new HallStatusRepo();
    }
    return instance;
  }

  private HallStatusRepo() {
  }

  List<HallStatus> findAllHallStatuses() throws DBException {
    List<HallStatus> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(Sql.FIND_ALL);
      while (rs.next()) {
        list.add(factory.createHallStatus(rs));
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


  HallStatus findHallStatusById(long id) throws DBException {
    HallStatus hallStatus = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        hallStatus = factory.createHallStatus(rs);
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
    return hallStatus;
  }

  HallStatus findHallByStatus(String status) throws DBException {
    HallStatus hallStatus = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_STATUS);
      stmt.setString(1, status);
      rs = stmt.executeQuery();
      if (rs.next()) {
        hallStatus = factory.createHallStatus(rs);
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
    return hallStatus;
  }
}
