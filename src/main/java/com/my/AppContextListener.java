package com.my;

import com.my.utils.DbUtils;
import com.my.db.connection.MySqlConnection;
import com.my.exception.DBException;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

  private final static Logger LOG = Logger.getLogger(AppContextListener.class);


  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext ctx = sce.getServletContext();
    Connection con = null;
    try {
      con = MySqlConnection.getInstance().getConnection();
      LOG.info(Logs.CONNECTION_ESTABLISHED);
    } catch (DBException e) {
      LOG.error(e.getMessage());
    }
    ctx.setAttribute("DbConnection", con);

    String path = ctx.getRealPath("WEB-INF/log4j.log");
    System.setProperty("logFile", path);

  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    ServletContext ctx = servletContextEvent.getServletContext();
    Connection con = (Connection) ctx.getAttribute("DbConnection");
    try {
      DbUtils.close(con);
    } catch (DBException e) {
      LOG.error(e.getMessage());
    }
    LOG.info("Database connection closed for Application.");
  }
}
