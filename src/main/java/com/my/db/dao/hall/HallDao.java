package com.my.db.dao.hall;

import com.my.db.dao.Dao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import com.my.utils.constants.SortingOptionsHall;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;

public class HallDao implements Dao {

  private static HallDao instance;
  private static final HallRepo hallRepo = HallRepo.getInstance();

  public static synchronized HallDao getInstance() {
    if (instance == null) {
      instance = new HallDao();
    }
    return instance;
  }

  @Override
  public List<Hall> findAll() throws DBException {
    return hallRepo.findAllHalls();
  }

  @Override
  public Hall findById(long id) throws DBException {
    return hallRepo.findByIdentifier(id);
  }

  @Override
  public void update(HttpServletRequest req) {

  }

  public void update(Hall hall, String status) throws DBException {
    hallRepo.updateInstance(hall, status);
  }

  @Override
  public void create(HttpServletRequest req) throws DBException {
    hallRepo.createHall(req);
  }

  @Override
  public void delete(long id) throws DBException {

  }

  public List<Hall> findByStatus(String status) throws DBException {
    return hallRepo.getByStatus(status);
  }

  public int getHallCount() throws DBException {
    return hallRepo.getCount();
  }

  public List<Hall> findAllLimitOffset(int offset, int limit) throws DBException {
    return hallRepo.getAllLimitOffset(offset, limit);

  }

  public List<Hall> findAllLimitOffsetGreater(int offset, int limit, String hallStatus, int space)
      throws DBException {
    String sql = Sql.FIND_GREATER_OFFSET_LIMIT_BY_STATUS;
    return hallRepo.getHallsByStatusAndSpaceLimitOffset(offset, limit, hallStatus, space, sql);
  }

  public List<Hall> findAllLimitOffsetSmaller(int offset, int limit, String hallStatus, int space)
      throws DBException {
    String sql = Sql.FIND_SMALLER_OFFSET_LIMIT_BY_STATUS;
    return hallRepo.getHallsByStatusAndSpaceLimitOffset(offset, limit, hallStatus, space, sql);
  }

  public List<Hall> findAllLimitOffsetByStatus(int offset, int limit, String status)
      throws DBException {
    String sql = Sql.FIND_OFFSET_LIMIT_BY_STATUS_STRING;
    return hallRepo.getHallsSetString(offset, limit, status, sql);
  }

  public List<Hall> findAllLimitOffsetAreaGreaterThan(int offset, int limit, int space)
      throws DBException {
    String sql = Sql.FIND_OFFSET_LIMIT_WHERE_SPACE_GREATER;
    return hallRepo.getHallsSetInt(offset, limit, space, sql);
  }

  public List<Hall> findAllLimitOffsetAreaLessThan(int offset, int limit, int space)
      throws DBException {
    String sql = Sql.FIND_OFFSET_LIMIT_WHERE_SPACE_SMALLER;
    return hallRepo.getHallsSetInt(offset, limit, space, sql);
  }

  public List<Hall> sort(String sortingOption, String order, int limit, int offset)
      throws DBException {
    String sql;
    List<String> allowedOptions = Stream.of(SortingOptionsHall.values())
        .map(SortingOptionsHall::name)
        .collect(Collectors.toList());

    if (!Utils.containsOption(allowedOptions, sortingOption)) {
      throw new DBException(Logs.NO_SUCH_SORTING_OPTION);
    }
    if (order == null || order.equalsIgnoreCase(Params.ASC)) {
      sql = String.format(Sql.FIND_ALL_LIMIT_OFFSET_ORDER, sortingOption);
    } else {
      sql = String.format(Sql.FIND_ALL_LIMIT_OFFSET_ORDER_DESC, sortingOption);
    }
    return hallRepo.findAllLimitOffsetOrder(sql, limit, offset);
  }

  public Hall findByHallNo(int num) throws DBException {
    return hallRepo.findHallByNo(num);
  }
}