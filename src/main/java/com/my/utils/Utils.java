package com.my.utils;

import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.role.RoleDao;
import com.my.db.dao.user.UserDao;
import com.my.entity.Exhibition;
import com.my.entity.User;
import com.my.entity.UserRole;
import com.my.exception.CommandException;
import com.my.exception.DBException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.service.UserService;
import com.my.utils.constants.Logs;
import com.my.validator.Validator;
import com.my.utils.constants.Params;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class);
  private static final ExhibitionDao DAO = ExhibitionDao.getInstance();
  private static String errorMes;

  private Utils() {
  }

  /**
   * Retrieves all request params from request. To be used if ALL input parameters must not be null
   *
   * @return Map of all values pertaining to respective request parameters
   * @throws ValidationException if at least one input param is null
   */
  public static Map<String, String[]> verifyNoneIsNull(HttpServletRequest req)
      throws ValidationException {

    Map<String, String[]> requestParams = getAllParams(req);

    requestParams.remove(Params.COMMAND);

    for (Map.Entry<String, String[]> map : requestParams.entrySet()) {
      if (map.getKey() == null || map.getValue() == null) {
        errorMes = Logs.NULL_NOT_ALLOWED;
        LOG.warn(errorMes);
        throw new ValidationException(errorMes);
      }
    }
    return requestParams;
  }

  public static Map<String, String[]> getAllParams(HttpServletRequest req) {
    return Collections.list(req.getParameterNames())
        .stream()
        .collect(Collectors.toMap(parameterName -> parameterName, req::getParameterValues));
  }


  public static void validateNotNull(String param) throws CommandException {
    if (param == null) {
      errorMes = Logs.NULL_NOT_ALLOWED;
      LOG.warn(errorMes);
      throw new CommandException(errorMes);
    }
  }

  public static User getUserWithNewLogin(HttpServletRequest req)
      throws DBException, ServiceException, ValidationException {
    String newLogin = req.getParameter(Params.LOGIN);
    User user = (User) req.getSession().getAttribute(Params.CURRENT_USER);

    if (user == null) {
      errorMes = Logs.NULL_NOT_ALLOWED;
      LOG.warn(errorMes);
      throw new ValidationException(errorMes);
    }

    Validator.validateNotNull(newLogin);
    verifyLogin(user, newLogin);
    user.setLogin(newLogin);
    return user;
  }

  public static User getUserWithNewPassword(HttpServletRequest req)
      throws ServiceException, DBException, ValidationException {
    User user = (User) req.getSession().getAttribute(Params.CURRENT_USER);

    if (user == null ) {
      errorMes = Logs.NULL_NOT_ALLOWED;
      LOG.warn(errorMes);
      throw new ValidationException(errorMes);
    }

    Validator.validateRequestParamsNotNull(req);
    long id = user.getId();
    String psw = retrievePass(id);
    String oldPass = req.getParameter(Params.CURRENT_PASS);
    String newPass = req.getParameter(Params.NEW_PASS);
    String confirmPass = req.getParameter(Params.CONFIRM_PASS);
    verifyOldPass(oldPass, psw);
    verifyNewPass(newPass, confirmPass);
    user.setPassword(UserService.getInstance().hash(newPass));
    return user;
  }

  public static User getUserWithNewRole(HttpServletRequest req)
      throws CommandException, DBException, ValidationException {
    long userId = retrieveId(req.getParameter(Params.USER_ID));
    User user = UserDao.getInstance().findById(userId);

    String role = req.getParameter(Params.USER_ROLE);

    Validator.validateNotNull(role);
    boolean isExist = RoleDao.getInstance().isExist(role);
    if (!isExist) {
      throw new CommandException("Role " + role + " does not exist");
    }
    UserRole newRole = RoleDao.getInstance().findByUserRole(role);
    user.setRole(newRole);
    return user;
  }

  public static int parseIntVal(String val) throws ValidationException {
    String num = val.replaceAll("[^0-9]", "");
    Validator.validateNotEmpty(num);
    return Integer.parseInt(num);
  }

  public static boolean verifyExhibitionStatus(String status) {
    return status.equalsIgnoreCase(Params.CURRENT) || status.equalsIgnoreCase(Params.PENDING);
  }

  public static int getOffset(int pageSize, int page) {
    if (page < 1) {
      return 0;
    }
    return pageSize * (page - 1);
  }

  /**
   * Sets session attributes for pagination
   *
   * @param req      to set attributes
   * @param page     from respective request
   * @param pageSize from respective request
   * @param count    passed from Command
   */

  public static void getPagination(HttpServletRequest req, int page, int pageSize, int count) {
    int shift = 0;
    int minPagePossible = Math.max(page - shift, 1);
    int pageCount;
    int maxPagePossible;

    pageCount = (int) Math.ceil((double) count / pageSize);
    maxPagePossible = Math.min(page + shift, pageCount);

    if (page > maxPagePossible) {
      page = maxPagePossible;
    }
    req.setAttribute(Params.PAGE_COUNT, pageCount);
    req.setAttribute(Params.PAGE, page);
    req.setAttribute(Params.PAGE_SIZE, pageSize);
    req.setAttribute(Params.MIN_PAGE, minPagePossible);
    req.setAttribute(Params.MAX_PAGE, maxPagePossible);
  }

  public static void getCurrentExhibitionList(HttpServletRequest req) throws DBException {
    List<Exhibition> list;
    HttpSession session = req.getSession();
    int exhibitionCount;
    int pageSize = Integer.parseInt(req.getParameter(Params.PAGE_SIZE));
    int page = Integer.parseInt(req.getParameter(Params.PAGE));
    int offset = Utils.getOffset(pageSize, page);

    exhibitionCount = DAO.getCurrentExhibitionCount();
    list = DAO.findCurrentExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize);
    Utils.getPagination(req, page, pageSize, exhibitionCount);
    session.setAttribute(Params.EXHIBITIONS_LIST, list);
  }

  public static void getAllExhibitionList(HttpServletRequest req) throws DBException {
    List<Exhibition> list;
    HttpSession session = req.getSession();
    int exhibitionCount;
    int pageSize = Integer.parseInt(req.getParameter(Params.PAGE_SIZE));
    int page = Integer.parseInt(req.getParameter(Params.PAGE));
    int offset = Utils.getOffset(pageSize, page);

    exhibitionCount = DAO.getAllExhibitionCount();
    list = DAO.findAllExhibitionsWithOffsetAndLimitNoFilter(offset, pageSize);
    Utils.getPagination(req, page, pageSize, exhibitionCount);
    session.setAttribute(Params.EXHIBITIONS_LIST, list);
  }

  public static long retrieveId(String id) throws ValidationException {
    Validator.validateNotNull(id);
    Validator.validateInt(id);
    return Long.parseLong(id);
  }

  public static int getInt(String val) throws ValidationException {
    Validator.validateNotNull(val);
    Validator.validateInt(val);
    return Integer.parseInt(val);
  }

  public static boolean containsOption(List<String> enumValues, String sortingOption) {
    for (String enumOption : enumValues) {
      if (sortingOption.equalsIgnoreCase(enumOption)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method verifies that entered value is different from that assigned to current user. Method
   * verifies that value is different from any existing in DB thus eliminating duplicated users.
   *
   * @param user     - current user from session
   * @param newLogin - params entered by user
   */
  private static void verifyLogin(User user, String newLogin) throws ServiceException, DBException {
    if (newLogin.equals(user.getLogin())) {
      errorMes = Logs.ALREADY_SIGNED_IN;
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }

    if (UserDao.getInstance().isExists(newLogin)) {
      errorMes = Logs.USER_EXISTS;
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }
  }

  private static String retrievePass(long id) throws DBException {
    User user = UserDao.getInstance().findById(id);
    return user.getPassword();
  }

  private static void verifyNewPass(String newPass, String confirmPass) throws ServiceException {
    String pass = UserService.getInstance().hash(newPass);
    String confirm = UserService.getInstance().hash(confirmPass);
    if (!pass.equals(confirm)) {
      errorMes = Logs.PASS_CONFIRMATION_FAILED;
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }
  }

  private static void verifyOldPass(String input, String stored) throws ServiceException {
    String pass = UserService.getInstance().hash(input);
    if (!pass.equals(stored)) {
      errorMes = Logs.OLD_PASS_DOESNT_MATCH;
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }
  }
}
