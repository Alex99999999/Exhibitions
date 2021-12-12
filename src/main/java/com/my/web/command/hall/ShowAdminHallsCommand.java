package com.my.web.command.hall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.db.dao.hallStatus.HallStatusDao;
import com.my.entity.Hall;
import com.my.entity.HallStatus;
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

public class ShowAdminHallsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowAdminHallsCommand.class);

  /**
   * Retrieves all halls and their statuses
   *
   * @param req Contains pagination params
   * @return Page displaying retrieved info. List of halls and statuses are attached to the session
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    HallDao dao = HallDao.getInstance();
    List<Hall> hallList;
    List<HallStatus> statusList;
    int pageSize;
    int page;
    int hallCount;
    int offset;
    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      offset = Utils.getOffset(pageSize, page);
      hallCount = dao.getHallCount();
      Utils.getPagination(req, page, pageSize, hallCount);
      hallList = dao.findAllLimitOffset(offset, pageSize);
      statusList = HallStatusDao.getInstance().findAll();

    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    session.setAttribute(Params.HALL_LIST, hallList);
    session.setAttribute(Params.HALL_STATUS_LIST, statusList);

    return Jsp.ADMIN_HALLS_LIST;
  }
}