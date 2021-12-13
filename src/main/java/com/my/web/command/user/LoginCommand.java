package com.my.web.command.user;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.service.UserService;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {

  private static final Logger LOG = Logger.getLogger(LoginCommand.class);

  /**
   * Method verifies user has been registered with the system. Verifies login and password
   * credentials. Validates role assigned to a user and stored in DB and appoints user's role in
   * session depending on info received therefrom.
   *
   *
   * @param req Must contain "login" and "password" entered by user from login page.
   * @return Depends upon the result of method execution:
   * <ul>
   *   <li> Page displaying a list of current exhibitions if user role is not Admin</li>
   *   <li> Admin page if role is Admin</li>
   *   <li> Login page if either login or password is wrong, info message attached</li>
   *   <li> Error page in case of major exception</li>
   * </ul>
   */
  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    User user;
    String login;
    String password;
    String userRole;
    String hashedPsw;
    HttpSession session = req.getSession();

    try {
      Utils.verifyNoneIsNull(req);
      login = req.getParameter(Params.LOGIN);
      password = req.getParameter(Params.PASSWORD);
      user = UserDao.getInstance().findByLogin(login);
      if (user == null) {
        LOG.warn("User with login " + login + " not found");
        session.setAttribute(Params.LOGIN_ERROR, Logs.ACCESS_DENIED);
        return Jsp.LOGIN_PAGE;
      }

      hashedPsw = UserService.getInstance().hash(password);

      if (!user.getPassword().equals(hashedPsw)) {
        LOG.warn("Wrong password : " + password);
        session.setAttribute(Params.LOGIN_ERROR,  Logs.ACCESS_DENIED);
        return "users?command=show_login_page";
      }

      userRole = UserDao.getInstance().findRole(user);

      session.setAttribute(Params.CURRENT_USER, user);
      session.setMaxInactiveInterval(30 * 60);

      if (userRole.equalsIgnoreCase(Params.ADMIN)) {
        session.setAttribute(Params.SESSION_ROLE, Params.ADMIN);
        return "admin?command=show_admin_page";
      }

      session.setAttribute(Params.SESSION_ROLE, Params.AUTHORIZED_USER);
      return "users?page=1&pageSize=5&command=get_current_exhibitions_list";

    } catch (DBException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ServiceException | ValidationException e) {
      LOG.error(e.getMessage());
      return Jsp.LOGIN_PAGE;
    }
  }
}
