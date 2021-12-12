package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.entity.Exhibition;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.service.ExhibitionService;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class AdminUpdateExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AdminUpdateExhibitionCommand.class);
  private static final String INFO_MESSAGE = "Exhibition \"%s\" has been successfully updated";
  private static final ExhibitionService SERVICE = ExhibitionService.getInstance();
  private static final ExhibitionDao DAO = ExhibitionDao.getInstance();

  /**
   * Method to update fields changed from UI. Null values are not allowed except "Description" field
   * Validates time ranges and status of exhibition to be updated. Start time may not be after end
   * time. You may not change status to current if end date is before or start date is after current
   * date. You may not change status to pending if start date is before current date. You may assign
   * any status except "current" and "pending" to the exhibitions end-time of which is in the past.
   *
   * @return the page, request was made from, with info message about a mistake if any attached to
   * the session
   * @param req Contains params for all fields requisite for exhibition update
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    long id;

    try {
      id = Utils.retrieveId(req.getParameter(Params.EXHIBITION_ID));
      SERVICE.validateInput(req);
      DAO.update(req);
      Exhibition exhibition = DAO.findById(id);

      session.setAttribute(Params.EXHIBITION, exhibition);
      session
          .setAttribute(Params.INFO_MESSAGE, String.format(INFO_MESSAGE, exhibition.getTopic()));
    } catch (DBException | ServiceException | ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.INFO_MESSAGE, e.getMessage());
    }
    return Jsp.SHOW_ADMIN_EDIT_PAGE_COMMAND;
  }
}
