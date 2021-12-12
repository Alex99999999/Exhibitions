package com.my.db.dao.exhibitionStatus;

import com.my.entity.ExhibitionStatus;
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

  public List<ExhibitionStatus> findAll() throws DBException {
    return ExhibitionStatusRepo.getInstance().findAllStatuses();
  }

  public ExhibitionStatus findById(long id) throws DBException {
    return ExhibitionStatusRepo.getInstance().findByIdentifier(id);
  }
}
