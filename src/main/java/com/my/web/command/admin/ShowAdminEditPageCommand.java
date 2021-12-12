package com.my.web.command.admin;

import com.my.command.Command;
import com.my.entity.Exhibition;
import com.my.exception.DateFormatException;
import com.my.exception.ValidationException;
import com.my.service.ExhibitionService;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Logs;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowAdminEditPageCommand implements Command {

  private static final Logger LOG = Logger.getLogger(ShowAdminEditPageCommand.class);

  /**
   * @return Admin exhibition edit page view
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {

    Exhibition ex = (Exhibition) req.getSession().getAttribute(Params.EXHIBITION);
    try {
      Validator.validateNotNull(ex);
      parseDate(ex);

    } catch (ValidationException | DateFormatException e) {
      LOG.warn(e.getMessage());
      req.getSession().setAttribute(Params.INFO_MESSAGE, Logs.NOTHING_FOUND_PER_YOUR_REQUEST);
      return Jsp.ADMIN_EXHIBITION_EDIT_PAGE;
    }
    req.getSession().setAttribute(Params.EXHIBITION, ex);
    return Jsp.ADMIN_EXHIBITION_EDIT_PAGE;
  }


  private void parseDate(Exhibition ex) throws ValidationException, DateFormatException {
    String startDate = ex.getStartDate();
    String endDate = ex.getEndDate();
    if (!startDate.contains("-")) {
      startDate = ExhibitionService.getInstance().toDbDateFormat(startDate);
      ex.setStartDate(startDate);
    }
    if (!endDate.contains("-")) {
      endDate = ExhibitionService.getInstance().toDbDateFormat(endDate);
      ex.setEndDate(endDate);
    }
  }
}
