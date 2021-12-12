package com.my.web.command.exhibiitonHall;

import com.my.command.Command;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.exhibitionHalls.ExhibitionHallsDao;
import com.my.db.dao.hall.HallDao;
import com.my.entity.Exhibition;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SetHallsForExhibitionCommand implements Command {

  private static final Logger LOG = Logger.getLogger(SetHallsForExhibitionCommand.class);
  private static final String ADDRESS = "admin?command=show_halls_for_exhibition";
  private static final String INFO_MESSAGE = "May not be applied to exhibition \"%s\"";


  /**
   * Set halls for exhibition. Applies validation for exhibition status. A hall may be assigned only
   * to current or pending exhibition.
   * <p>
   * Also applies validation for hall status. Assigned may be a hall with "free" status only
   *
   * @param req Must contain "exhibition_id" and "free_hall_id" params
   * @return The page request has came from
   */


  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    Map<String, String[]> map;
    String[] hallIds;
    long exhibitionId;
    try {
      map = Utils.verifyNoneIsNull(req);
      exhibitionId = Utils.retrieveId(req.getParameter(Params.EXHIBITION_ID));
      Exhibition ex = ExhibitionDao.getInstance().findById(exhibitionId);
      Validator.validateKey(map, Params.FREE_HALL_ID);
      hallIds = map.get(Params.FREE_HALL_ID);
      if (hallIds.length == 0) {
        return ADDRESS;
      }
      setHalls(hallIds, ex, req);
    } catch (DBException e) {
      LOG.error(e.getMessage());
      return Jsp.ERROR_PAGE;
    } catch (ValidationException e) {
      req.setAttribute(Params.INFO_MESSAGE, e.getMessage());
      return ADDRESS;
    }
    req.setAttribute(Params.EXHIBITION_ID, exhibitionId);
    return ADDRESS;
  }


  private void setHalls(String[] hallIds, Exhibition ex, HttpServletRequest req)
      throws DBException {
    boolean exhibitionStatus = Utils.verifyExhibitionStatus(ex.getStatus().getStatus());
    for (String s : hallIds) {
      long hallId = Long.parseLong(s);
      boolean isFree = verifyHallIsFree(hallId);
      if (exhibitionStatus && isFree) {
        ExhibitionHallsDao.getInstance().setHallForExhibition(ex.getId(), hallId);
      } else {
        req.setAttribute(Params.INFO_MESSAGE, String.format(INFO_MESSAGE, ex.getTopic()));
      }
    }
  }

  private boolean verifyHallIsFree(long hallId) throws DBException {
    Hall hall = HallDao.getInstance().findById(hallId);
    return hall.getStatus().getStatus().equalsIgnoreCase(Params.FREE);
  }
}