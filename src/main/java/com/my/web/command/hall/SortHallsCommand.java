package com.my.web.command.hall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SortHallsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(SortHallsCommand.class);
  private HallDao dao = HallDao.getInstance();

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<Hall> sortedList = new ArrayList<>();
    String sortingOption = req.getParameter(Params.SORTING_OPTION);
    String order = req.getParameter(Params.ORDER);
    int hallCount;
    int page;
    int pageSize;
    int offset;
    try {
      Validator.validateNotNull(req.getParameter(Params.SORTING_OPTION));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      offset = Utils.getOffset(pageSize, page);
      hallCount = dao.getHallCount();
      sortedList = dao.sort(sortingOption, order, pageSize, offset);
      Utils.getPagination(req, page, pageSize, hallCount);
    } catch (ValidationException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.HALL_LIST, sortedList);
    req.setAttribute(Params.SORTING_OPTION, sortingOption);
    req.setAttribute(Params.ORDER, order);
    return Jsp.ADMIN_HALLS_LIST;
  }
}
