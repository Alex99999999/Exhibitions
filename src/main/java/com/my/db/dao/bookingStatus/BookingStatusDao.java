package com.my.db.dao.bookingStatus;

import com.my.db.dao.StatusRepo;
import com.my.entity.Status;
import com.my.exception.DBException;
import java.util.List;

public class BookingStatusDao {

  private static BookingStatusDao instance;
  private static final StatusRepo STATUS_REPO  = StatusRepo.getInstance();

  public static synchronized BookingStatusDao getInstance() {
    if (instance == null) {
      instance = new BookingStatusDao();
    }
    return instance;
  }

  public List<Status> findAll() throws DBException {
    String sql = Sql.FIND_ALL;
    return STATUS_REPO.findAll(sql);
  }

  public Status findById(long id) throws DBException {
    String sql = Sql.FIND_BY_ID;
    return STATUS_REPO.findById(sql, id);
  }
}
