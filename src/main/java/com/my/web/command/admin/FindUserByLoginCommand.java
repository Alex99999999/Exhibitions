package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.role.RoleDao;
import com.my.db.dao.user.UserDao;
import com.my.entity.Status;
import com.my.entity.User;
import com.my.entity.UserRole;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class FindUserByLoginCommand implements Command {

  private static final Logger LOG = Logger.getLogger(FilterUsersCommand.class);
  private static final UserDao DAO = UserDao.getInstance();
  private static final String ADDRESS = Jsp.ADMIN_USERS_DETAILS_PAGE;

  /**
   * Retrieves single user info
   *
   * @param req Contains users login
   * @return Page displaying information pertaining to the user with the given login if any
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<User> user;
    List<UserRole> roleList;
    HttpSession session = req.getSession();
    String login = req.getParameter(Params.LOGIN);

    try {
      Validator.validateNotNull(login);
      user = DAO.findByLoginLike(login);

      LOG.debug("----> " + user.size());

      if (user.size() == 0) {
        session.setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
        return "admin?command=show_admin_users&page=1&pageSize=5";
      }

      roleList = RoleDao.getInstance().findAll();
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return ADDRESS;
    }

    req.setAttribute(Params.USER_LIST, user);
    req.setAttribute(Params.USER_ROLE_LIST, roleList);
    return ADDRESS;
  }
}
