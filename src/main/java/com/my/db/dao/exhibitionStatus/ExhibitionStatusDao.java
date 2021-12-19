package com.my.db.dao.exhibitionStatus;

import com.my.db.dao.StatusRepo;
import com.my.entity.Status;
import com.my.exception.DBException;
import java.util.List;

public class ExhibitionStatusDao {

  private static ExhibitionStatusDao instance;

  public static synchronized ExhibitionStatusDao getInstance() {
    if (instance == null) {
      instance = new ExhibitionStatusDao();
    }
    return instance;
  }

  public List<Status> findAll() throws DBException {
    String sql = Sql.FIND_ALL;
    return StatusRepo.getInstance().findAll(sql);
  }

  public Status findById(long id) throws DBException {
    String sql = Sql.FIND_BY_ID;
    return StatusRepo.getInstance().findById(sql, id);
  }
}
