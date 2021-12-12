package com.my.db.dao.hallStatus;

import com.my.entity.HallStatus;
import com.my.exception.DBException;
import java.util.List;

public class HallStatusDao {

  private static HallStatusDao instance;
  private static final  HallStatusRepo HALL_STATUS_REPO = HallStatusRepo.getInstance();

  public static synchronized HallStatusDao getInstance() {
    if (instance == null) {
      instance = new HallStatusDao();
    }
    return instance;
  }

  private HallStatusDao() {
  }

  public List<HallStatus> findAll() throws DBException {
    return HALL_STATUS_REPO.findAllHallStatuses();
  }

  public HallStatus findById(long id) throws DBException {
    return HALL_STATUS_REPO.findHallStatusById(id);
  }

  public HallStatus findByStatus(String status) throws DBException {
    return HALL_STATUS_REPO.findHallByStatus(status);
  }
}
