package com.my.db.dao.exhibition;

import com.my.db.dao.Dao;
import com.my.utils.constants.SortingOptionsExhibition;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.utils.constants.Logs;
import com.my.utils.Utils;
import com.my.utils.constants.Params;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ExhibitionDao implements Dao {

  private static ExhibitionDao instance;
  private static final Logger LOG = Logger.getLogger(ExhibitionDao.class);
  private static final ExhibitionRepo EXHIBITION_REPO = ExhibitionRepo.getInstance();

  public static synchronized ExhibitionDao getInstance() {
    if (instance == null) {
      instance = new ExhibitionDao();
    }
    return instance;
  }

  @Override
  public List<Exhibition> findAll() throws DBException {
    return EXHIBITION_REPO.findAllExhibitions();
  }

  @Override
  public Exhibition findById(long id) throws DBException {
    return EXHIBITION_REPO.findByIdentifier(id);
  }

  @Override
  public void update(HttpServletRequest req) throws DBException {
    String sql = Sql.UPDATE_EXHIBITION;
    EXHIBITION_REPO.update(req, sql);
  }

  @Override
  public void create(HttpServletRequest req) throws DBException {
    String sql = Sql.CREATE_EXHIBITION;
    EXHIBITION_REPO.create(req, sql);
  }

  @Override
  public void delete(long id) throws DBException {
    EXHIBITION_REPO.deleteById(id);
  }

  public Exhibition findByTopic(String topic) throws DBException {
    return EXHIBITION_REPO.getByTopic(topic);
  }

  public void update(Exhibition ex) throws DBException {
    EXHIBITION_REPO.updateExhibition(ex);
  }

  public List<Exhibition> findCurrentExhibitionsWithOffsetAndLimitNoFilter(int offset, int pageSize)
      throws DBException {
    String query = Sql.FIND_CURRENT_OFFSET_LIMIT_NO_FILTER;
    return EXHIBITION_REPO.getExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize, query);
  }

  public List<Exhibition> findAllExhibitionsWithOffsetAndLimitNoFilter(int offset, int pageSize)
      throws DBException {
    String query = Sql.FIND_ALL_OFFSET_LIMIT_NO_FILTER;
    return EXHIBITION_REPO.getExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize, query);
  }

  /**
   * Method introduces a whitelist of allowed sorting options in the form of Enum
   * @return List of all exhibitions for admin
   */

  public List<Exhibition> sortAll(String sortingOption, String order, int offset, int pageSize)
      throws DBException {
    String sql;
    List<String> allowedOptions = Stream.of(SortingOptionsExhibition.values())
        .map(SortingOptionsExhibition::name)
        .collect(Collectors.toList());

    if (!Utils.containsOption(allowedOptions, sortingOption)) {
      LOG.warn(Logs.NO_SUCH_SORTING_OPTION);
      throw new DBException(Logs.NO_SUCH_SORTING_OPTION);
    }
    if (order == null || order.equalsIgnoreCase(Params.ASC)) {
      sql = String.format(Sql.FIND_ALL_LIMIT_OFFSET_ORDER, sortingOption);
    } else if (sortingOption == null) {
      sql = String.format(Sql.FIND_ALL_LIMIT_OFFSET_ORDER, Params.PRICE);
    } else {
      sql = String.format(Sql.FIND_ALL_LIMIT_OFFSET_ORDER_DESC, sortingOption);
    }
    return EXHIBITION_REPO.getExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize, sql);
  }

  /**
   * @return List of current exhibitions for authorized user and user
   */
  public List<Exhibition> sortCurrent(String sortingOption, String order, int offset, int pageSize)
      throws DBException {
    String sql;
    List<String> allowedOptions = Stream.of(SortingOptionsExhibition.values())
        .map(SortingOptionsExhibition::name)
        .collect(Collectors.toList());

    if (!Utils.containsOption(allowedOptions, sortingOption)) {
      LOG.warn(Logs.NO_SUCH_SORTING_OPTION);
      throw new DBException(Logs.NO_SUCH_SORTING_OPTION);
    }
    if (order == null || order.equalsIgnoreCase(Params.ASC)) {
      sql = String.format(Sql.FIND_CURRENT_LIMIT_OFFSET_ORDER, sortingOption);
    } else {
      sql = String.format(Sql.FIND_CURRENT_LIMIT_OFFSET_ORDER_DESC, sortingOption);
    }
    return EXHIBITION_REPO.getExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize, sql);
  }

  public List<Exhibition> filterAllByDateOffsetLimit(String userDate, int offset, int pageSize)
      throws DBException {
    String sql = Sql.FILTER_ALL_BY_DATE;
    return EXHIBITION_REPO.filterByDateOffsetLimit(userDate, offset, pageSize, sql);
  }

  public List<Exhibition> filterCurrentByDateOffsetLimit(String userDate, int offset, int pageSize)
      throws DBException {
    String sql = Sql.FILTER_CURRENT_BY_DATE;
    return EXHIBITION_REPO.filterByDateOffsetLimit(userDate, offset, pageSize, sql);
  }

  public int getCurrentExhibitionCount() throws DBException {
    String sql = Sql.GET_CURRENT_EXHIBITION_COUNT;
    return EXHIBITION_REPO.getCount(sql);
  }

  public int getAllExhibitionCount() throws DBException {
    String sql = Sql.GET_ALL_EXHIBITION_COUNT;
    return EXHIBITION_REPO.getCount(sql);
  }
}
