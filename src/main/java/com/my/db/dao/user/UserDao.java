package com.my.db.dao.user;

import com.my.db.dao.Dao;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import com.my.utils.constants.SortingOptionsUser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class UserDao implements Dao {

  private static final Logger LOG = Logger.getLogger(UserDao.class);
  private static UserDao instance;
  private static UserRepo repo = UserRepo.getInstance();

  private UserDao() {
  }

  public static synchronized UserDao getInstance() {
    if (instance == null) {
      instance = new UserDao();
    }
    return instance;
  }

  @Override
  public List<User> findAll() throws DBException {
    return repo.getAllUsers();
  }

  @Override
  public User findById(long id) throws DBException {
    return repo.getById(id);
  }

  /**
   * Used by Login command
   *
   * @param login value entered by user when signing in
   * @return User whose login exactly coincide with that stored in DB
   */
  public User findByLogin(String login) throws DBException {
    return repo.getUserByStringValue(Sql.FIND_BY_LOGIN, login);
  }

  /**
   * Used by admin search user command
   *
   * @param login value entered by admin to boost search of a user
   * @return User whose login contains value entered by admin
   */
  public User findByLoginLike(String login) throws DBException {
    String query = "%" + login + "%";
    return repo.getUserByStringValue(Sql.GET_BY_LOGIN_LIKE, query);
  }

  @Override
  public void update(HttpServletRequest req) throws DBException {
    repo.updateUser(req);
  }

  @Override
  public void create(HttpServletRequest req) throws DBException {
    repo.insertUser(req);
  }

  @Override
  public void delete(long id) throws DBException {
    repo.deleteUser(id);
  }

  public boolean isExists(String login) throws DBException {
    return repo.isStored(login);
  }

  public String findRole(User user) throws DBException {
    return repo.getRole(user.getRole().getId());

  }

  public void update(User user) throws DBException {
    repo.updateUser(user);
  }

  /**
   * For the purposes of pagination
   *
   * @return int total amount of all users in DB
   */
  public int getUserCount() throws DBException {
    return repo.getCount();
  }

  public List<User> findAllLimitOffset(int offset, int limit) throws DBException {
    String sql = Sql.FIND_USERS_LIMIT_OFFSET;
    return repo.getUsersLimitOffset(sql, offset, limit);
  }

  /**
   * For the purposes of pagination used with filter
   *
   * @return int  total count of users having the specified role
   */
  public int getUserCountByRole(String userRole) throws DBException {
    return repo.getUsersCountByRole(userRole);
  }

  /**
   * For the purposes of pagination used with filter
   *
   * @return list of users having the specified role
   */
  public List<User> findByRoleLimitOffset(String userRole, int limit, int offset)
      throws DBException {
    return repo.getUsersByRoleLimitOffset(userRole, limit, offset);
  }

  public List<User> sortAllLimitOffset(String sortingOption, String order, int offset, int limit)
      throws DBException {
    String sql;
    List<String> allowedOptions = Stream.of(SortingOptionsUser.values())
        .map(SortingOptionsUser::name)
        .collect(Collectors.toList());

    if (!Utils.containsOption(allowedOptions, sortingOption)) {
      LOG.warn(Logs.NO_SUCH_SORTING_OPTION);
      throw new DBException(Logs.NO_SUCH_SORTING_OPTION);
    }

    if (order == null || order.equalsIgnoreCase(Params.ASC)) {
      sql = String.format(Sql.SORT_ALL_BY_COLUMN, sortingOption);
    } else {
      sql = String.format(Sql.SORT_ALL_BY_COLUMN_DESC, sortingOption);
    }
    return repo.getUsersLimitOffset(sql, offset, limit);
  }
}


