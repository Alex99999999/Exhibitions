package com.my.db.connection;

import com.my.exception.DBException;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MySqlConnection implements ConnectionManager {

  private static final Logger LOG = LogManager.getLogger(MySqlConnection.class);

  private static MySqlConnection instance;
  private DataSource ds;
  private String errorMes;

  public static synchronized MySqlConnection getInstance() {
    if (instance == null) {
      instance = new MySqlConnection();
    }
    return instance;
  }

  private MySqlConnection() {
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup("java:/comp/env");
      ds = (DataSource) envContext.lookup("jdbc/exhibitions");
    } catch (NamingException ex) {
      errorMes = Logs.DATA_SOURCE_SEARCH_ERROR;
      LOG.error(errorMes);
      throw new IllegalStateException(errorMes);
    }
  }

  @Override
  public Connection getConnection() throws DBException {
    Connection con;
    try {
      con = ds.getConnection();
    } catch (SQLException ex) {
      errorMes = Logs.CONNECTION_ERROR;
      LOG.error(errorMes);
      throw new DBException(errorMes);
    }
    return con;
  }
}
