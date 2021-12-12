package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetExhibitionByTopic implements Command {

  private static final Logger LOG = Logger.getLogger(GetExhibitionByTopic.class);

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Exhibition ex;
    String topic = req.getParameter(Params.TOPIC);

    try {
      ex = ExhibitionDao.getInstance().findByTopic(topic);
      if (ex == null) {
        req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
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
