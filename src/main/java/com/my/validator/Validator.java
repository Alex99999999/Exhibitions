package com.my.validator;

import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class Validator {

  private static final Logger LOG = Logger.getLogger(Validator.class);
  private static String errorMes;

  public static void validateNotNull(Object param) throws ValidationException {
    if (param == null || param.equals("")) {
      errorMes = Logs.NULL_NOT_ALLOWED;
      LOG.warn(errorMes);
      throw new ValidationException(errorMes);
    }
  }

  public static void validateRequestParamsNotNull(HttpServletRequest req) throws ValidationException {
    Map<String, String[]> requestParams = Utils.getAllParams(req);
    for (Map.Entry<String, String[]> map : requestParams.entrySet()) {
      if (!map.getKey().equalsIgnoreCase(Params.DESCRIPTION) && map.getValue() == null) {
        errorMes = Logs.NULL_NOT_ALLOWED;
        LOG.warn(errorMes);
        throw new ValidationException(errorMes);
      }
    }
  }

  public static void validateInt(String val) throws ValidationException {
    validateNotNull(val);
    containsDigitsOnly(val);
  }

  static void validateDouble(String val) throws ValidationException {
    validateNotNull(val);
  }

  private static void containsDigitsOnly(String val) throws ValidationException {
    val = val.replaceAll("[0-9]", "");

    LOG.debug("-----> val " + val );

    if (!val.isEmpty()) {
      errorMes = Logs.MUST_BE_A_NUMBER;
      LOG.warn(errorMes);
      throw new ValidationException(errorMes);
    }
  }

  public static void validateKey(Map<String, String[]> map, String key)
      throws ValidationException {
    if (!map.containsKey(key)) {
      errorMes = Logs.VALUE_NOT_FOUND;
      LOG.warn(errorMes);
      throw new ValidationException (errorMes + key);
    }
  }

  public static void validateNotEmpty(String str) throws ValidationException {
    if (str.isEmpty()) {
      throw new ValidationException(Logs.EMPTY_PARAM);
    }
  }

  public static void ValidateHallInput(HttpServletRequest req) throws ValidationException {
    String floor = req.getParameter(Params.HALL_FLOOR);
    String floorSpace = req.getParameter(Params.HALL_FLOOR_SPACE);
    String hallNo = req.getParameter(Params.HALL_NO);
    Validator.validateInt(floor);
    Validator.validateInt(hallNo);
    Validator.validateDouble(floorSpace);
  }

}
