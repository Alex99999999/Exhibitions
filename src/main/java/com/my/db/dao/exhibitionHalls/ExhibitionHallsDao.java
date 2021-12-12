package com.my.db.dao.exhibitionHalls;

import com.my.entity.Hall;
import com.my.exception.DBException;
import java.util.List;
import org.apache.log4j.Logger;

public class ExhibitionHallsDao {

  private static final Logger LOG = Logger.getLogger(ExhibitionHallsDao.class);
  private static ExhibitionHallsDao instance;
  private ExhibitionHallsRepo repo = ExhibitionHallsRepo.getInstance();

  private ExhibitionHallsDao() {
  }

  public static synchronized ExhibitionHallsDao getInstance() {
    if (instance == null) {
      instance = new ExhibitionHallsDao();
    }
    return instance;
  }

  public List<Hall> findHallsByExhibitionId(long id) throws DBException {
    return repo.getHallsByExhibitionId(id);
  }

  public void deleteHallFromExhibitions(long id) throws DBException {
    repo.deleteHall(id);
  }

  public void setHallForExhibition(long exhibitionId, long hallId) throws DBException {
    repo.setHall(exhibitionId, hallId);
  }

  public int findCountByExhibitionId(long id) throws DBException {
    return repo.findCountByExhibitionId(id);
  }
}
