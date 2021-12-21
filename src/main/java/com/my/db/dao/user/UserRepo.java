package com.my.db.dao.user;

import com.my.utils.DbUtils;
import com.my.entity.User;
import com.my.entity.UserRole;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.service.UserService;
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

class UserRepo {

  private static final Logger LOG = Logger.getLogger(UserRepo.class);
  private static final Factory factory = Factory.getInstance();
  private static UserRepo instance;
  private static String errorMes;
  private static final String DEFAULT_ROLE = Params.AUTHORIZED_USER;

  private UserRepo() {
  }

  static synchronized UserRepo getInstance() {
    if (instance == null) {
      instance = new UserRepo();
    }
    return instance;
  }

  List<User> getAllUsers() throws DBException {
    List<User> list;
    Connection con = DbUtils.getCon();
    try {
      list = getAllUsers(con, Sql.FIND_ALL);
    } finally {
      DbUtils.close(con);
    }
    return list;
  }


  User getById(long id) throws DBException {
    User user;
    Connection con = DbUtils.getCon();
    try {
      user = getUserByStringValue(con, Sql.FIND_BY_ID, id);
    } finally {
      DbUtils.close(con);
    }
    return user;
  }

  void updateUser(HttpServletRequest req) throws DBException {
    int roleId = Integer.parseInt(req.getParameter(Params.USER_ROLES_ID));

    if (isStored(roleId)) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(Sql.UPDATE_USER);
      String login = req.getParameter(Params.LOGIN);
      String psw = req.getParameter(Params.PASSWORD);
      roleId = Integer.parseInt(req.getParameter(Params.USER_ROLES_ID));
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(login));
      stmt.setString(++k, DbUtils.escapeForPstmt(psw));
      stmt.setLong(++k, roleId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UPDATE_USER_ERROR + "with id" + roleId;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  void insertUser(HttpServletRequest req) throws DBException {
    String login = "";
    String psw;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      login = req.getParameter(Params.LOGIN);
      psw = UserService.getInstance().hash(req.getParameter(Params.PASSWORD));
      stmt = con.prepareStatement(Sql.CREATE_USER);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(login));
      stmt.setString(++k, DbUtils.escapeForPstmt(psw));
      stmt.setString(++k, DEFAULT_ROLE);
      stmt.executeUpdate();
    } catch (SQLException | ServiceException e) {
      errorMes = Logs.CREATE_USER_ERROR + "with login " + login;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  User getUserByStringValue(String query, String val) throws DBException {
    User user = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setString(1, val);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = factory.createUser(rs);
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
    return user;
  }

  List<User> getUsersLike(String query, String val) throws DBException {
    List<User> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setString(1, val);
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createUser(rs));
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

  private User getUserByStringValue(Connection con, String query, long val) throws DBException {
    User user = null;
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setLong(1, val);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        user = factory.createUser(rs);
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }

    return user;
  }

  private List<User> getAllUsers(Connection con, String query) throws DBException {
    List<User> list = new ArrayList<>();
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        list.add(factory.createUser(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }

    return list;
  }

  void deleteUser(long id) throws DBException {
    if (isStored(id)) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
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
    User user = getById(id);
    return user == null;
  }

  boolean isStored(String login) throws DBException {
    User user = getUserByStringValue(Sql.FIND_BY_LOGIN, login);
    return user != null;
  }

  String getRole(long id) throws DBException {
    UserRole role = null;
    ResultSet rs = null;
    try (Connection con = DbUtils.getCon();
        PreparedStatement stmt = con.prepareStatement(Sql.GET_ROLE)) {
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        role = factory.createUserRole(rs);
      }
      if (role == null) {
        errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
        LOG.error(errorMes);
        throw new DBException(errorMes);
      }
    } catch (SQLException e) {
      errorMes = Logs.NOTHING_FOUND_PER_YOUR_REQUEST;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    } finally {
      DbUtils.close(rs);
    }
    return role.getRole();
  }

  void updateUser(User user) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      String login = user.getLogin();
      String psw = user.getPassword();
      long roleId = user.getRole().getId();
      long userId = user.getId();
      stmt = con.prepareStatement(Sql.UPDATE_USER);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(login));
      stmt.setString(++k, DbUtils.escapeForPstmt(psw));
      stmt.setLong(++k, roleId);
      stmt.setLong(++k, userId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      errorMes = Logs.UPDATE_USER_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
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
      errorMes = Logs.UNABLE_TO_GET_COUNT;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return count;
  }

  List<User> getUsersLimitOffset(String sql, int offset, int limit) throws DBException {
    List<User> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(sql);
      stmt.setInt(1, limit);
      stmt.setInt(2, offset);
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createUser(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_CREATE_USER_LIST;
      LOG.error(errorMes);
      throw new DBException(errorMes, e);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }

  int getUsersCountByRole(String userRole) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int count = 0;
    try {
      stmt = con.prepareStatement(Sql.GET_COUNT_BY_ROLE);
      stmt.setString(1, DbUtils.escapeForPstmt(userRole));
      rs = stmt.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
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
    return count;
  }

  List<User> getUsersByRoleLimitOffset(String userRole, int limit, int offset) throws DBException {
    List<User> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_USERS_BY_ROLE_LIMIT_OFFSET);
      int k = 0;
      stmt.setString(++k, DbUtils.escapeForPstmt(userRole));
      stmt.setInt(++k, limit);
      stmt.setInt(++k, offset);
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createUser(rs));
      }
    } catch (SQLException e) {
      errorMes = Logs.UNABLE_TO_CREATE_USER_LIST;
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