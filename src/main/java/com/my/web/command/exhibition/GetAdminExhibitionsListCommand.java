package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.currency.CurrencyDao;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.exhibitionStatus.ExhibitionStatusDao;
import com.my.entity.Currency;
import com.my.entity.Exhibition;
import com.my.entity.ExhibitionStatus;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GetAdminExhibitionsListCommand implements Command {

  private static final Logger LOG = Logger.getLogger(GetAdminExhibitionsListCommand.class);
  private static final ExhibitionDao DAO = ExhibitionDao.getInstance();

  /**
   * To access this method role in session must be Admin only.
   * <p>
   * Method retrieves full information on all exhibitions existing in DB irrespectively of their
   * status and date.
   * </p>
   *
   * @param req Must contain params for pagination, and user role
   * @return Admin page where all the information about exhibitions available in DB is displayed.
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    List<Exhibition> list;
    List<ExhibitionStatus> statusList;
    List<Currency> currency;
    int pageSize;
    int page;
    int offset;
    int exhibitionCount;

    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));

      statusList = ExhibitionStatusDao.getInstance().findAll();
      currency = CurrencyDao.getInstance().findAll();
      exhibitionCount = DAO.getAllExhibitionCount();
      offset = Utils.getOffset(pageSize, page);

      list = DAO.findAllExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize);
      Utils.getPagination(req, page, pageSize, exhibitionCount);

    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.setAttribute(Params.EXHIBITION_STATUS_LIST, statusList);
    req.setAttribute(Params.CURRENCY_LIST, currency);
    req.setAttribute(Params.EXHIBITIONS_LIST, list);
    return Jsp.ADMIN_EXHIBITIONS_LIST;
  }
}