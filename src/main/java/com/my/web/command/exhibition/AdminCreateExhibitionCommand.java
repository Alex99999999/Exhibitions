package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.service.ExhibitionService;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AdminCreateExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AdminCreateExhibitionCommand.class);
  private ExhibitionService service = ExhibitionService.getInstance();

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String topic = req.getParameter(Params.TOPIC);
    try {
      service.validateInput(req);
      ExhibitionDao.getInstance().create(req);
      req.getSession().setAttribute(Params.INFO_MESSAGE,
          "Exhibition \"" + topic + "\" has been successfully created");
    } catch (DBException | ServiceException | ValidationException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    return "admin?command=show_admin_create_page";
  }
}
