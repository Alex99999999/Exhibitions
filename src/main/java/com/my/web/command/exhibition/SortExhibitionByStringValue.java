package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SortExhibitionByStringValue implements Command {

  private static final Logger LOG = Logger.getLogger(SortExhibitionByStringValue.class);
  private ExhibitionDao dao = ExhibitionDao.getInstance();

  /**
   * Method implements sorting and pagination Verifies user role and delivers respective selection
   * in DB: current exhibitions only for users and all exhibitions for admin. Default sorting option
   * is price.
   *
   * @param req should contain sorting option and order. May be nullable.
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String role = (String) req.getSession().getAttribute(Params.SESSION_ROLE);
    String sortingOption = req.getParameter(Params.SORTING_OPTION);
    String order = req.getParameter(Params.ORDER);
    String address = Jsp.ERROR_PAGE;
    int exhibitionCount;
    int page;
    int pageSize;
    int offset;
    boolean isAdmin = role != null && role.equalsIgnoreCase(Params.ADMIN);
    boolean isSortingOptionEmpty = sortingOption == null || sortingOption.equals("");

    if (isSortingOptionEmpty && order != null) {
      sortingOption = Params.PRICE;
    }

    if (isSortingOptionEmpty && order == null) {
      if (isAdmin){
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
        address = getAdminAddress(req, sortingOption, offset, pageSize, order);
      } else {
        exhibitionCount = dao.getCurrentExhibitionCount();
        Utils.getPagination(req, page, pageSize, exhibitionCount);
        address = getUserAddress(req, sortingOption, offset, pageSize, order);
      }

    } catch (ValidationException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return address;
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return address;
    }
    req.getSession().setAttribute(Params.SORTING_OPTION, sortingOption);
    req.getSession().setAttribute(Params.ORDER, order);
    return address;
  }

  private String getUserAddress(HttpServletRequest req, String sortingOption, int offset,
      int pageSize, String order) {
    List<Exhibition> ex;
    try {
      ex = dao.sortCurrent(sortingOption, order, offset, pageSize);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
  }


  private String getAdminAddress(HttpServletRequest req, String sortingOption, int offset,
      int pageSize, String order) {
    List<Exhibition> ex;
    try {
      ex = dao.sortAll(sortingOption, order, offset, pageSize);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.ADMIN_EXHIBITIONS_LIST;
  }
}
