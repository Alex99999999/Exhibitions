package com.my.db.dao.exhibitionHalls;

import com.my.utils.DbUtils;
import com.my.db.dao.hall.HallDao;
import com.my.db.dao.hallStatus.HallStatusDao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

class ExhibitionHallsRepo {

  private static final Logger LOG = Logger.getLogger(ExhibitionHallsRepo.class);
  private static ExhibitionHallsRepo instance;
  private static String mes;
  private HallDao hallDao = HallDao.getInstance();

  private ExhibitionHallsRepo() {
  }

  static synchronized ExhibitionHallsRepo getInstance() {
    if (instance == null) {
      instance = new ExhibitionHallsRepo();
    }
    return instance;
  }

  List<Hall> getHallsByExhibitionId(long id) throws DBException {
    List<Hall> list = new ArrayList<>();
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.prepareStatement(Sql.GET_HALLS_BY_EXHIBITION_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
//      if(!rs.next()) {
//        return list;
//      }
      while (rs.next()) {
        list.add(hallDao.findById(rs.getLong(Params.HALL_ID)));
      }
    } catch (SQLException e) {
      mes = "Unable to find halls pertaining to current exhibition";
      LOG.error(mes);
      throw new DBException(mes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
    return list;
  }

  /**
   * Method utilizes transaction. This method deletes the relation between exhition and hall and
   * sets hall status to "FREE"
   *
   * @param id object with the given id is handled and changed throughout the transaction
   * @throws DBException the cause should be specified in the error message for there are a lot of
   *                     places and a lot of reason it may be thrown because of
   */

  void deleteHall(long id) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      con.setAutoCommit(false);
      stmt = con.prepareStatement(Sql.DELETE_HALL_FROM_EXHIBITION);
      stmt.setLong(1, id);
      stmt.executeUpdate();
      Hall hall = hallDao.findById(id);
      hall.setStatus(HallStatusDao.getInstance().findByStatus(Params.FREE));
      hallDao.update(hall, Params.FREE);
      con.commit();
    } catch (SQLException e) {
      mes = Logs.DELETION_ERROR;
      LOG.error(mes);
      DbUtils.rollback(con);
      throw new DBException(mes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  /**
   * Method to assign hall to exhibition. One hall may be occupied by one exhibition only One
   * exhibition may occupy several halls
   *
   * @param exhibitionId value from session
   * @param hallId       value from session
   * @throws DBException see  error message in logs
   */
  void setHall(long exhibitionId, long hallId) throws DBException {
    Hall hall;
    Connection con = DbUtils.getCon();
    PreparedStatement stmt = null;
    try {
      con.setAutoCommit(false);
      hall = hallDao.findById(hallId);
      hallDao.update(hall, Params.OCCUPIED);
      stmt = con.prepareStatement(Sql.SET_HALL_FOR_EXHIBITION);
      stmt.setLong(1, exhibitionId);
      stmt.setLong(2, hallId);
      stmt.executeUpdate();
      con.commit();
    } catch (SQLException e) {
      mes = "Unable to create relationship hall id" + hallId + "exhibition id" + exhibitionId;
      LOG.error(mes);
      DbUtils.rollback(con);
      throw new DBException(mes);
    } finally {
      DbUtils.close(stmt);
      DbUtils.close(con);
    }
  }

  int findCountByExhibitionId(long id) throws DBException {
    Connection con = DbUtils.getCon();
    PreparedStatement ps = null;
    ResultSet rs = null;
    int hallCount = 0;
    try {
      ps = con.prepareStatement(Sql.FIND_BY_EXHIBITION_ID);
      ps.setLong(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        hallCount = rs.getInt(1);
      }
    } catch (SQLException e) {
      mes = Logs.UNABLE_TO_GET_COUNT;
      LOG.error(mes);
      throw new DBException(mes);
    } finally {
      DbUtils.close(rs);
      DbUtils.close(ps);
      DbUtils.close(con);
    }
    return hallCount;
  }
}
