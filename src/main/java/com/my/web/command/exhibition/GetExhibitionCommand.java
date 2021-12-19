package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.currency.CurrencyDao;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.exhibitionStatus.ExhibitionStatusDao;
import com.my.entity.Currency;
import com.my.entity.Exhibition;
import com.my.entity.Status;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(GetExhibitionCommand.class);

  /**
   * Retrieves full information on the selected item from the entire list of Exhibitions.
   * @param req contains id of the exhibition the full info on which is to be displayed
   * @return page address to display info of error page address in case of critical error
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Exhibition exhibition;
    List<Status> exStatList;
    List<Currency> curList;
    long id;
    try {
      id = Utils.retrieveId(req.getParameter("id"));
      exhibition = ExhibitionDao.getInstance().findById(id);
      exStatList = ExhibitionStatusDao.getInstance().findAll();
      curList = CurrencyDao.getInstance().findAll();
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITION, exhibition);
    req.getSession().setAttribute(Params.EXHIBITION_STATUS_LIST,exStatList);
    req.getSession().setAttribute(Params.CURRENCY_LIST, curList);

    return Jsp.EXHIBITION_DETAILS_PAGE;
  }
}
