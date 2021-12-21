package com.my.web.command.exhibition;

import com.my.command.Command;
import com.my.db.dao.booking.BookingDao;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.exhibitionHalls.ExhibitionHallsDao;
import com.my.entity.Exhibition;
import com.my.exception.CommandException;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AdminDeleteExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AdminDeleteExhibitionCommand.class);
  private ExhibitionDao dao = ExhibitionDao.getInstance();

  /**
   * Method removes any info on selected exhibition Verifies availability of bookings and halls
   * assigned to the selected instance
   *
   * @param req contains Exhibition object stored in session
   * @return Error page if exhibition has active bookings (paid or booked) of active halls assigned
   * to it. If none of the above, returns page with info about successful deletion
   */

  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    long id;
    Exhibition ex;
    try {
      id = Utils.retrieveId(req.getParameter(Params.EXHIBITION_ID));
      ex = dao.findById(id);
      verifyHalls(id);
      verifyBookings(id);
      dao.delete(id);
    } catch (DBException | ValidationException | CommandException e) {
      LOG.error(e.getMessage());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    req.getSession().setAttribute(Params.INFO_MESSAGE,
        "Exhibition \"" + ex.getTopic() + "\" has been successfully removed");
    return "admin?command=get_exhibitions_list&page=1&pageSize=5";
  }


  private void verifyHalls(long id) throws DBException, CommandException {
    int hallCount = ExhibitionHallsDao.getInstance().findCountByExhibitionId(id);
    if (hallCount != 0) {
      throw new CommandException("Exhibition has halls assigned!");
    }
  }

  private void verifyBookings(long id) throws CommandException, DBException {
    int bookingCount = BookingDao.getInstance()
        .findCountByExhibitionIdAndStatus(id, "booked", "paid");
    if (bookingCount != 0) {
      throw new CommandException("Exhibition has active bookings!");
    }
  }

}