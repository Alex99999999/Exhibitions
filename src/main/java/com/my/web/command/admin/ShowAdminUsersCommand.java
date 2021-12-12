package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.role.RoleDao;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.entity.UserRole;
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

public class ShowAdminUsersCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowAdminUsersCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    UserDao dao = UserDao.getInstance();
    List<UserRole> roleList;
    List<User> userList;
    int pageSize;
    int page;
    int userCount;
    int offset;

    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      roleList = RoleDao.getInstance().findAll();
      userCount = dao.getUserCount();
      offset = Utils.getOffset(pageSize, page);
      Utils.getPagination(req, page, pageSize, userCount);
      userList = dao.findAllLimitOffset(offset, pageSize);

    } catch (DBException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      LOG.warn(e.getMessage());
      session.setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.USER_LIST, userList);
    session.setAttribute(Params.USER_ROLE_LIST, roleList);
    return Jsp.ADMIN_USERS_PAGE;
  }
}
