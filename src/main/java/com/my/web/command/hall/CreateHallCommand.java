package com.my.web.command.hall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CreateHallCommand implements Command {

  private static final Logger LOG = Logger.getLogger(CreateHallCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    try {
      Validator.ValidateHallInput(req);
      HallDao.getInstance().create(req);
      req.getSession().setAttribute(Params.INFO_MESSAGE,
          "Hall has been successfully created");
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return "admin?command=show_admin_create_hall_page";
  }

}
