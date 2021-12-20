package com.my.db.dao;

import com.my.entity.Status;
import com.my.exception.DBException;
import com.my.utils.DbUtils;
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

public class StatusRepo {

  private static StatusRepo instance;
  private static final Logger LOG = Logger.getLogger(StatusRepo.class);
  private static final Factory factory = Factory.getInstance();
  private String errorMes;

  public static synchronized StatusRepo getInstance() {
    if (instance == null) {
      instance = new StatusRepo();
    }
    return instance;
  }

  private StatusRepo() {
  }

  public List<Status> findAll(String sql) throws DBException {
    List<Status> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        list.add(factory.createStatus(rs));
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


  public Status findById(String sql, long id) throws DBException {
    Status status = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(sql);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        status = factory.createStatus(rs);
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

  public Status findByString(String sql, String status) throws DBException {
    Status stat = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(sql);
      stmt.setString(1, DbUtils.escapeForPstmt(status));
      rs = stmt.executeQuery();
      if (rs.next()) {
        stat = factory.createStatus(rs);
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
    return stat;
  }
}
