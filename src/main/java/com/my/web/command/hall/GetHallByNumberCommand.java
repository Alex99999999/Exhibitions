package com.my.web.command.hall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetHallByNumberCommand implements Command {

  private static final Logger LOG = Logger.getLogger(GetHallByNumberCommand.class);
  private static final String COMMAND = "admin?page=1&pageSize=5&command=show_admin_halls";

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Hall hall;
    int num;
    String param = req.getParameter(Params.HALL_NO);

    if (param == null || param.length() > 8) {
      req.setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      return COMMAND;
    }

    try {
      num = Utils.getInt(param);
      hall = HallDao.getInstance().findByHallNo(num);

      if (hall == null) {
        req.setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
        return COMMAND;
      }

    } catch (ValidationException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return COMMAND;
    } catch (DBException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.HALL, hall);
    return Jsp.ADMIN_HALL_PAGE;

  }
}
