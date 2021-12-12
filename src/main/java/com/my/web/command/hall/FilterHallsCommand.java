package com.my.web.command.hall;

import com.my.command.Command;
import com.my.db.dao.hall.HallDao;
import com.my.entity.Hall;
import com.my.exception.DBException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class FilterHallsCommand implements Command {

  private static final Logger LOG = Logger.getLogger(FilterHallsCommand.class);
  private static final HallDao DAO = HallDao.getInstance();

  /**
   * Implements pagination Filters halls involving our options in their combination of max 2 at a
   * time.
   *
   * @param req Contains hall_status and floor_space filtering options. May be nullable.
   * @return Filtered hall list attached to the session
   */
  @Override
  public String execute(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    List<Hall> hallList;
    int pageSize;
    int page;
    int hallCount;
    int offset;
    String hallStatus = req.getParameter(Params.HALL_STATUS);
    String floorSpace = req.getParameter(Params.FLOOR_SPACE);
    try {
      pageSize = Utils.getInt(req.getParameter(Params.PAGE_SIZE));
      page = Utils.getInt(req.getParameter(Params.PAGE));
      offset = Utils.getOffset(pageSize, page);
      if (hallStatus == null && floorSpace == null) {
        hallList = DAO.findAllLimitOffset(offset, pageSize);
      } else if (hallStatus == null && floorSpace != null) {
        hallList = floorSpaceOptions(offset, pageSize, floorSpace);
      } else if (hallStatus != null && floorSpace == null) {
        hallList = hallStatusOptions(offset, pageSize, hallStatus);
      } else {
        hallList = combinedOptions(offset, pageSize, hallStatus, floorSpace);
      }
      hallCount = DAO.getHallCount();

      Utils.getPagination(req, page, pageSize, hallCount);
    } catch (DBException | ValidationException e) {
      LOG.error(e.getMessage());
      session.setAttribute(Params.ERROR_MESSAGE, e.getMessage());
      return Jsp.ERROR_PAGE;
    }
    session.setAttribute(Params.HALL_LIST, hallList);
    return Jsp.ADMIN_HALLS_LIST;
  }

  private List<Hall> combinedOptions(int offset, int pageSize, String hallStatus, String floorSpace)
      throws DBException, ValidationException {
    List<Hall> hallList;
    int space = Utils.parseIntVal(floorSpace);
    boolean isFree = hallStatus.equalsIgnoreCase(Params.FREE);
    boolean isOccupied = hallStatus.equalsIgnoreCase(Params.OCCUPIED);
    boolean isGreater = floorSpace.contains(">");
    boolean isSmaller = floorSpace.contains("<");
    if (isFree && isGreater) {
      hallList = DAO.findAllLimitOffsetGreater(offset, pageSize, hallStatus, space);
    } else if (isFree && isSmaller) {
      hallList = DAO.findAllLimitOffsetSmaller(offset, pageSize, hallStatus, space);
    } else if (isOccupied && isGreater) {
      hallList = DAO.findAllLimitOffsetGreater(offset, pageSize, hallStatus, space);
    } else {
      hallList = DAO.findAllLimitOffsetSmaller(offset, pageSize, hallStatus, space);
    }
    return hallList;
  }

  private List<Hall> hallStatusOptions(int offset, int pageSize, String status) throws DBException {
    List<Hall> hallList;
    if (status.equalsIgnoreCase(Params.FREE)) {
      hallList = DAO.findAllLimitOffsetByStatus(offset, pageSize, status);
    } else if (status.equalsIgnoreCase(Params.OCCUPIED)) {
      hallList = DAO.findAllLimitOffsetByStatus(offset, pageSize, status);
    } else {
      hallList = null;
    }
    return hallList;
  }

  private List<Hall> floorSpaceOptions(int offset, int limit, String floorSpace)
      throws DBException, ValidationException {
    List<Hall> hallList;
    int space = Utils.parseIntVal(floorSpace);
    if (floorSpace.contains(">")) {
      hallList = DAO.findAllLimitOffsetAreaGreaterThan(offset, limit, space);
    } else if (floorSpace.contains("<")) {
      hallList = DAO.findAllLimitOffsetAreaLessThan(offset, limit, space);
    } else {
      hallList = null;
    }
    return hallList;
  }

}
