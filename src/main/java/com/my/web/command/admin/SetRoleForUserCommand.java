package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.CommandException;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SetRoleForUserCommand implements Command {

  private static Logger LOG = Logger.getLogger(SetRoleForUserCommand.class);

  /**
   * Assigns role for user and retrieves list of users afterwards with the one with role changed
   *
   * @param req must contain user ID
   * @return admin users list page with updated info on user
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    User user;
    List<User> list;
    int page;
    int pageSize;
    int offset;
    try {
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      offset = Utils.getOffset(pageSize, page);
      user = Utils.getUserWithNewRole(req);
      UserDao.getInstance().update(user);
      list = UserDao.getInstance().findAllLimitOffset(offset, pageSize);
    } catch (DBException | CommandException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.USER, list);
    return Jsp.ADMIN_USERS_PAGE;
  }
}
