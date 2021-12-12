package com.my.web.command.exhibiitonHall;

import com.my.command.Command;
import com.my.db.dao.exhibitionHalls.ExhibitionHallsDao;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteHallFromExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(DeleteHallFromExhibitionCommand.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    long hallId;
    try {
      hallId = Utils.retrieveId(req.getParameter(Params.HALL_ID));
      ExhibitionHallsDao.getInstance().deleteHallFromExhibitions(hallId);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return "admin?command=show_halls_for_exhibition";
  }
}
