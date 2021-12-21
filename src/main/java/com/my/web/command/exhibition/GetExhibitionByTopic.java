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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class GetExhibitionByTopic implements Command {

  private static final Logger LOG = Logger.getLogger(GetExhibitionByTopic.class);
  private ExhibitionDao dao = ExhibitionDao.getInstance();

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String address;
    String role = (String) req.getSession().getAttribute(Params.SESSION_ROLE);
    HttpSession session = req.getSession();

    try {
      if (role != null && role.equalsIgnoreCase(Params.ADMIN)) {
        address = getAdminAddress(req);
      } else {
        address = getUserAddress(req);
      }
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return address;
  }

  private String getUserAddress(HttpServletRequest req) throws ValidationException, DBException {
    List<Exhibition> ex;
    int pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
    int page = Utils.getInt(req.getParameter(Params.PAGE));
    int exhibitionCount = dao.getCurrentExhibitionCount();
    String topic = req.getParameter(Params.TOPIC);

    Utils.getPagination(req, page, pageSize, exhibitionCount);
    ex = dao.findCurrentExhibitionsOffsetLimitByTopic(0, pageSize, topic);
    if (ex.size() == 0) {
      req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      return "users?command=get_current_exhibitions_list&page=1&pageSize=5";
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
  }

  private String getAdminAddress(HttpServletRequest req)
      throws DBException, ValidationException {
    List<Exhibition> ex;
    String topic = req.getParameter(Params.TOPIC);
    int pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
    int page = Utils.getInt(req.getParameter(Params.PAGE));
    int exhibitionCount = dao.getAllExhibitionCount();

    Utils.getPagination(req, page, pageSize, exhibitionCount);
    ex = dao.findByTopic(0, pageSize, topic);
    if (ex.size() == 0) {
      req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      Utils.getAllExhibitionList(req);
      return Jsp.ADMIN_EXHIBITIONS_LIST;
    }
    req.getSession().setAttribute(Params.EXHIBITIONS_LIST, ex);
    return Jsp.ADMIN_EXHIBITIONS_LIST;
  }
}
