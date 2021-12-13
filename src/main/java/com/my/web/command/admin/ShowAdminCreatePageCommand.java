package com.my.web.command.admin;

import com.my.command.Command;
import com.my.db.dao.currency.CurrencyDao;
import com.my.db.dao.exhibitionStatus.ExhibitionStatusDao;
import com.my.entity.Currency;
import com.my.entity.ExhibitionStatus;
import com.my.exception.DBException;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowAdminCreatePageCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowAdminCreatePageCommand.class);

  /**
   * Collects existing values for  currency and exhibition statuses.
   * @return Admin exhibition create page view
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {

    List<Currency> currency;
    List<ExhibitionStatus> status;

    try {
      currency = CurrencyDao.getInstance().findAll();
      status = ExhibitionStatusDao.getInstance().findAll();
    } catch (DBException e) {
      LOG.error(e.getMessage());
      return Jsp.ERROR_PAGE;
    }

    req.setAttribute(Params.CURRENCY_LIST, currency);
    req.setAttribute(Params.EXHIBITION_STATUS_LIST, status);

    return Jsp.ADMIN_EXHIBITION_CREATE_PAGE;
  }
}