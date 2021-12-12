package com.my.db.dao.exhibitionStatus;

import com.my.utils.DbUtils;
import com.my.entity.ExhibitionStatus;
import com.my.exception.DBException;
import com.my.utils.Factory;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ExhibitionStatusRepo {

  private String errorMes;
  private static ExhibitionStatusRepo instance;
  private static final Logger LOG = Logger.getLogger(ExhibitionStatusRepo.class);
  private static final Factory factory = Factory.getInstance();


  public static synchronized ExhibitionStatusRepo getInstance() {
    if (instance == null) {
      instance = new ExhibitionStatusRepo();
    }
    return instance;
  }

  List<ExhibitionStatus> findAllStatuses() throws DBException {
    Connection con = DbUtils.getCon();
    List<ExhibitionStatus> list = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_ALL);
      rs = stmt.executeQuery();
      while (rs.next()) {
        list.add(factory.createExhibitionStatus(rs));
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


  ExhibitionStatus findByIdentifier(long id) throws DBException {
    ExhibitionStatus status = null;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.FIND_BY_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      if (rs.next()) {
        status = factory.createExhibitionStatus(rs);
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
