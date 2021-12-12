package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class FilterExhibitionsByDateCommand implements Command {

  private static final Logger LOG = Logger.getLogger(FilterExhibitionsByDateCommand.class);
  private static final ExhibitionDao dao = ExhibitionDao.getInstance();

  /**
   * Filters exhibitions by date Returns list of exhibitions user date falls within the period of.
   * <p>
   * Returns address of page in respect to session role
   * </p>
   *
   * @param req contains input date
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String role = (String) req.getSession().getAttribute(Params.SESSION_ROLE);
    String userDate = req.getParameter(Params.INPUT_DATE);
    LOG.debug("----> " + role);
    LOG.debug("----> " + userDate);
    String address;
    int exhibitionCount;
    int page;
    int pageSize;
    int offset;
    boolean isAdmin = role != null && role.equalsIgnoreCase(Params.ADMIN);

    if (userDate == null) {
      if (isAdmin) {
        return Jsp.ADMIN_EXHIBITIONS_LIST;
      }
      return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
    }

    try {
      page = Utils.getInt(req.getParameter(Params.PAGE));
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      offset = Utils.getOffset(pageSize, page);

      if (isAdmin) {
        exhibitionCount = dao.getAllExhibitionCount();
        Utils.getPagination(req, page, pageSize, exhibitionCount);
        address = getAdminAddress(req, userDate, offset, pageSize);
      } else {
        exhibitionCount = dao.getCurrentExhibitionCount();
        Utils.getPagination(req, page, pageSize, exhibitionCount);
        address = getUserAddress(req, userDate, offset, pageSize);
      }
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return address;
  }


  private String getUserAddress(HttpServletRequest req, String userDate, int offset, int pageSize) {
    List<Exhibition> ex;
    try {
      ex = dao.filterCurrentByDateOffsetLimit(userDate, offset, pageSize);
      if (ex == null) {
        req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.DATE_FILTER_FAILURE);
        return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
      }
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
  }

  private String getAdminAddress(HttpServletRequest req, String userDate, int offset,
      int pageSize) {
    List<Exhibition> ex;
    try {
      ex = dao.filterAllByDateOffsetLimit(userDate, offset, pageSize);
      if (ex == null) {
        req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.DATE_FILTER_FAILURE);
        return Jsp.ADMIN_EXHIBITIONS_LIST;
      }
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.ADMIN_EXHIBITIONS_LIST;
  }
}
