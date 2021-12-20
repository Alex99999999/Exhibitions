package com.my.db.dao.role;

import com.my.utils.DbUtils;
import com.my.entity.UserRole;
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

public class RoleRepo {

  private static final Logger LOG = Logger.getLogger(RoleRepo.class);
  private static RoleRepo instance;
  private String errorMes;
  private static final Factory factory = Factory.getInstance();

  private RoleRepo() {
  }

  public static synchronized RoleRepo getInstance() {
    if (instance == null) {
      instance = new RoleRepo();
    }
    return instance;
  }

  List<UserRole> getAllRoles() throws DBException {
    List<UserRole> list;
    Connection con = DbUtils.getCon();
    try {
      list = getAllUserRoles(con, Sql.FIND_ALL);
    } finally {
      DbUtils.close(con);
    }
    return list;
  }

  UserRole getById(long id) throws DBException {
    UserRole role = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    ResultSet rs;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        role = factory.createUserRole(rs);
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return role;
  }

  UserRole getByRole(String role) throws DBException {
    UserRole userRole = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt;
    ResultSet rs;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ROLE);
      stmt.setString(1, DbUtils.escapeForPstmt(role));
      rs = stmt.executeQuery();
      if (rs.next()) {
        userRole = factory.createUserRole(rs);
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return userRole;
  }

  void updateRole(HttpServletRequest req) throws DBException {

  }

  void createRole(HttpServletRequest req) throws DBException {
    String role = (String) req.getAttribute(Params.SESSION_ROLE);
    if (isStored(role)) {
      LOG.error(Logs.ALREADY_EXISTS + role);
      throw new DBException(Logs.ALREADY_EXISTS + role);
    }
    try (Connection con = DbUtils.getCon();
        PreparedStatement stmt = con.prepareStatement(Sql.INSERT_ROLE)) {
      stmt.setString(1, DbUtils.escapeForPstmt(role));
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.INSERTION_ERROR;
      LOG.error(errorMes, e);
      throw new DBException(errorMes, e);
    }
  }

  void deleteRole(long id) throws DBException {
    if (!isStored(id)) {
      LOG.error(Logs.ROLE_SEARCH_ERROR);
      throw new DBException(Logs.ROLE_SEARCH_ERROR);
    }
    try (Connection con = DbUtils.getCon();
        PreparedStatement stmt = con.prepareStatement(Sql.DELETE_BY_ID)) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.DELETION_ERROR;
      LOG.error(errorMes, e);
      throw new DBException(errorMes, e);
    }
  }

  private boolean isStored(long id) throws DBException {
    UserRole userRole = getById(id);
    return userRole != null;
  }

  boolean isStored(String role) throws DBException {
    UserRole userRole = getByRole(role);
    return userRole != null;
  }

  private List<UserRole> getAllUserRoles(Connection con, String query) throws DBException {
    List<UserRole> list = new ArrayList<>();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        list.add(factory.createUserRole(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }
}
