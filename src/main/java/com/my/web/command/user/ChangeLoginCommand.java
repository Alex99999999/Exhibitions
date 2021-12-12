package com.my.web.command.user;

import com.my.command.Command;
import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ChangeLoginCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ChangeLoginCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    User user;
    try {
      user = Utils.getUserWithNewLogin(req);
      UserDao.getInstance().update(user);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ServiceException | ValidationException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.SHOW_USER_ACCOUNT;
    }
    req.getSession().setAttribute(Params.CURRENT_USER, user);
    req.getSession().setAttribute(Params.INFO_MESSAGE, "Login successfully changed");
    return Jsp.SHOW_USER_ACCOUNT;
  }
}
