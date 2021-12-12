package com.my.web.command.user;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class RegistrationCommand implements Command {

  private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

  /**
   * Method delivers saving a new user in DB. Password is hashed.
   *
   * @param req Must contain three parameters: Login, password and duplicated password.
   * @return Depends upon the result of method execution:
   * <ul>
   *   <li> Login page if registration is successful</li>
   *   <li> Registration page message attached if login has been already registered or passwords do not match</li>
   *   <li> Error page in case of major exception</li>
   * </ul>
   */
  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String login;
    String psw;
    String repeatPassword;
    String mes;
    try {
      Utils.verifyNoneIsNull(req);
      login = req.getParameter(Params.LOGIN);
      psw = req.getParameter(Params.PASSWORD);
      repeatPassword = req.getParameter(Params.REPEAT_PASSWORD);

      if (UserDao.getInstance().isExists(login)) {
        mes = "User with login " + login + " already exists";
        LOG.info(mes);
        req.setAttribute(Params.ERROR_MESSAGE, mes);
        return Jsp.REGISTRATION_PAGE;
      }

      if (!psw.equals(repeatPassword)) {
        mes = "Passwords don't match";
        LOG.warn(mes);
        req.setAttribute(Params.ERROR_MESSAGE, mes);
        return Jsp.REGISTRATION_PAGE;
      }

      UserDao.getInstance().create(req);
      return Jsp.LOGIN_PAGE;

    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
    }
    return Jsp.ERROR_PAGE;
  }
}
