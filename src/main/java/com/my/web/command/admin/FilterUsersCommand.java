package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class FilterUsersCommand implements Command {

  private static final Logger LOG = Logger.getLogger(FilterUsersCommand.class);
  private static final UserDao dao = UserDao.getInstance();

  /**
   * Provides filtering of users by role.
   *
   * @param req Must contain user_role param
   * @return Admin users page or error
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<User> list;
    String filterOption = req.getParameter(Params.USER_ROLE);
    int pageSize;
    int page;
    int offset;
    int userCount = 0;

    if (filterOption == null) {
      req.setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      return Jsp.ADMIN_USERS_PAGE;
    }

    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      offset = Utils.getOffset(pageSize, page);
      Utils.getPagination(req, page, pageSize, userCount);
      list = dao.findByRoleLimitOffset(filterOption, pageSize, offset);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    if (list.isEmpty()) {
      req.setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
    }
    req.setAttribute(Params.USER_LIST, list);
    return Jsp.ADMIN_USERS_PAGE;
  }
}
