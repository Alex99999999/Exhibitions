package com.my.utils;

import com.my.db.connection.MySqlConnection;
import com.my.exception.DBException;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DbUtils {

  private static Logger LOG = Logger.getLogger(DbUtils.class);

  public static void close(AutoCloseable ac) throws DBException {
    if (ac != null) {
      try {
        ac.close();
      } catch (Exception e) {
        LOG.error(Logs.UNABLE_TO_CLOSE);
        throw new DBException(Logs.UNABLE_TO_CLOSE, e);
      }
    }
  }

  public static Connection getCon() throws DBException {
    return MySqlConnection.getInstance().getConnection();
  }

  public static void rollback(Connection con) throws DBException {
    if (con != null) {
      try {
        con.rollback();
      } catch (SQLException e) {
        String errorMes = "Transaction rollback failure";
        LOG.error(errorMes);
        throw new DBException(errorMes);
      }
    }
  }

  public static String escapeSymbolsForPstmt(String input) {
    return input
        .replace("!", "!!")
        .replace("%", "!%")
        .replace("_", "!_")
        .replace("[", "![");
  }
}
