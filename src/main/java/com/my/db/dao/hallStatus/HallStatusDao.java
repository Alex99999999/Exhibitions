package com.my.db.dao.hallStatus;

import com.my.db.dao.StatusRepo;
import com.my.entity.Status;
import com.my.exception.DBException;
import java.util.List;

public class HallStatusDao {

  private static HallStatusDao instance;
  private static final StatusRepo REPO = StatusRepo.getInstance();

  public static synchronized HallStatusDao getInstance() {
    if (instance == null) {
      instance = new HallStatusDao();
    }
    return instance;
  }

  private HallStatusDao() {
  }

  public List<Status> findAll() throws DBException {
    String sql = Sql.FIND_ALL;
    return REPO.findAll(sql);
  }

  public Status findById(long id) throws DBException {
    String sql = Sql.FIND_BY_ID;
    return REPO.findById(sql, id);
  }

  public Status findByStatus(String status) throws DBException {
    String sql = Sql.FIND_BY_STATUS;
    return REPO.findByString(sql, status);
  }
}
