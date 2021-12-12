package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SortUsersCommand implements Command {

  private static final Logger LOG = Logger.getLogger(SortUsersCommand.class);
  private static final UserDao USER_DAO = UserDao.getInstance();

  /**
   * Returns limited list of users according to offset specified in request
   *
   * @param req Contains page and page size required for pagination
   * @return current list of users sorted by selected option in ascending by default order
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    int page;
    int pageSize;
    int offset;
    int userCount;
    List<User> userList;
    String order = req.getParameter(Params.ORDER);
    String sortingOption = req.getParameter(Params.SORTING_OPTION);
    boolean isSortingOptionEmpty = sortingOption == null || sortingOption.equals("");

    if (isSortingOptionEmpty && order != null) {
      sortingOption = Params.LOGIN;
    }

    if (isSortingOptionEmpty && order == null) {
      return Jsp.ADMIN_USERS_PAGE;
    }

    try {
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      offset = Utils.getOffset(pageSize, page);
      userCount = USER_DAO.getUserCount();
      userList = USER_DAO.sortAllLimitOffset(sortingOption, order, offset, pageSize);
      Utils.getPagination(req, page, pageSize, userCount);

    } catch (DBException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return Jsp.ADMIN_USERS_PAGE;
    }

    session.setAttribute(Params.USER_LIST, userList);
    req.setAttribute(Params.SORTING_OPTION, sortingOption);
    req.setAttribute(Params.ORDER, order);
    return Jsp.ADMIN_USERS_PAGE;
  }
}
