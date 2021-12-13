package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetExhibitionByTopic implements Command {

  private static final Logger LOG = Logger.getLogger(GetExhibitionByTopic.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Exhibition ex;
    String topic = req.getParameter(Params.TOPIC);
    String role = (String) req.getSession().getAttribute(Params.SESSION_ROLE);

    try {
      ex = ExhibitionDao.getInstance().findByTopic(topic);
      if (ex == null) {
        if (role.equalsIgnoreCase(Params.ADMIN)) {
          req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
          Utils.getAllExhibitionList(req);
          return Jsp.ADMIN_EXHIBITIONS_LIST;
        }
        req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
        Utils.getCurrentExhibitionList(req);
        return Jsp.PAGINATION_CURRENT_EXHIBITIONS_LIST;
      }
    } catch (DBException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITION, ex);
    return Jsp.EXHIBITION_DETAILS_PAGE;
  }
}
