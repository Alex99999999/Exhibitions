package com.my.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.my.db.dao.user.UserDao;
import com.my.entity.User;
import com.my.exception.CommandException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class UtilsTest {

  private static MockedStatic<Utils> utilities;
  private static HttpServletRequest mockRequest;
  private static HttpSession mockSession;
  private static UserDao mockDao;
  private static Map<String, String[]> map;
  private static User user;

  @BeforeAll
  public static void setup() {
    utilities = Mockito.mockStatic(Utils.class);
    mockRequest = mock(HttpServletRequest.class);
    mockSession = mock(HttpSession.class);
    mockDao = mock(UserDao.class);
    map = new HashMap<>();
    user = User.getInstance();
    user.setLogin("test");
    user.setPassword("test");
  }

  @AfterAll
  public static void tearDown() {
    utilities.close();
  }

  @Test
  void verifyNoneIsNullShouldThrowValidationExceptionIfParamValueIsNull() {
    when(mockRequest.getParameter(Params.TOPIC)).thenReturn("Test");
    when(mockRequest.getParameter(Params.PRICE)).thenReturn(null);
    map.put(Params.TOPIC, new String[]{"Test"});
    map.put(Params.PRICE, null);

    utilities.when(() -> Utils.verifyNoneIsNull(mockRequest))
        .thenCallRealMethod();
    utilities.when(() -> Utils.getAllParams(mockRequest))
        .thenReturn(map);

    Assertions.assertThrows(ValidationException.class,
        () -> Utils.verifyNoneIsNull(mockRequest));
  }

  @Test
  void verifyNoneIsNullShouldThrowValidationExceptionIfParamIsNull() {
    when(mockRequest.getParameter(Params.TOPIC)).thenReturn("Test");
    when(mockRequest.getParameter(Params.PRICE)).thenReturn("150");
    map.put(Params.TOPIC, new String[]{"Test"});
    map.put(null, new String[]{"150"});

    utilities.when(() -> Utils.verifyNoneIsNull(mockRequest))
        .thenCallRealMethod();
    utilities.when(() -> Utils.getAllParams(mockRequest))
        .thenReturn(map);

    Assertions.assertThrows(ValidationException.class,
        () -> Utils.verifyNoneIsNull(mockRequest));
  }


  @Test
  void verifyNoneIsNullShouldReturnMap() throws ValidationException {
    when(mockRequest.getParameter(Params.TOPIC)).thenReturn("Test");
    when(mockRequest.getParameter(Params.PRICE)).thenReturn(null);
    map.put(Params.TOPIC, new String[]{"Test"});
    map.put(Params.PRICE, new String[]{"250"});

    utilities.when(() -> Utils.verifyNoneIsNull(mockRequest))
        .thenCallRealMethod();
    utilities.when(() -> Utils.getAllParams(mockRequest))
        .thenReturn(map);

    Assertions.assertEquals(map, Utils.verifyNoneIsNull(mockRequest));
  }


  @Test
  void parseIntValShouldReturnInt() throws ValidationException {
    utilities.when(() -> Utils.parseIntVal("15"))
        .thenCallRealMethod();
    Assertions.assertEquals(15, Utils.parseIntVal("15"));
  }

  @Test
  void parseIntValShouldThrowValidationException() {
    utilities.when(() -> Utils.parseIntVal("dfsd"))
        .thenCallRealMethod();
    Assertions.assertThrows(ValidationException.class, () -> Utils.parseIntVal("dfsd"));
  }

  @Test
  void getAllParamsShouldReturnMap() {
    when(mockRequest.getParameter(Params.TOPIC)).thenReturn("Test");
    when(mockRequest.getParameter(Params.PRICE)).thenReturn("250");
    map.put(Params.TOPIC, new String[]{"Test"});
    map.put(Params.PRICE, new String[]{"250"});

    utilities.when(() -> Utils.getAllParams(mockRequest))
        .thenReturn(map);

    Map<String, String[]> reqMap = Utils.getAllParams(mockRequest);

    Assertions.assertEquals(map, reqMap);
  }

  @Test
  void validateNotNullShouldNotThrowValidationException() {
    utilities.when(() -> Utils.validateNotNull("12"))
        .thenCallRealMethod();
    Assertions.assertDoesNotThrow(() -> Utils.validateNotNull("12"));
  }

  @Test
  void validateNotNullShouldThrowValidationException() {
    utilities.when(() -> Utils.validateNotNull(null))
        .thenCallRealMethod();
    CommandException ex = Assertions
        .assertThrows(CommandException.class, () -> Utils.validateNotNull(null));
    Assertions.assertEquals(Logs.NULL_NOT_ALLOWED, ex.getMessage());
  }


  @Test
  void verifyExhibitionStatusShouldReturnTrueIfStatusIsCurrent() {
    utilities.when(() -> Utils.verifyExhibitionStatus(Params.CURRENT))
        .thenCallRealMethod();
    Assertions.assertTrue(Utils.verifyExhibitionStatus(Params.CURRENT));
  }

  @Test
  void verifyExhibitionStatusShouldReturnTrueIfStatusIsPending() {
    utilities.when(() -> Utils.verifyExhibitionStatus(Params.PENDING))
        .thenCallRealMethod();
    Assertions.assertTrue(Utils.verifyExhibitionStatus(Params.PENDING));
  }

  @Test
  void verifyExhibitionStatusShouldReturnFalseIfStatusIsNotCurrentOrPending() {
    utilities.when(() -> Utils.verifyExhibitionStatus(Params.CLOSED))
        .thenCallRealMethod();
    Assertions.assertFalse(Utils.verifyExhibitionStatus(Params.CLOSED));
  }

  @Test
  void getOffsetShouldReturnZeroIfNegativeValuePassed() {
    utilities.when(() -> Utils.getOffset(-2, 2)).thenCallRealMethod();
    Assertions.assertEquals(0, Utils.getOffset(0, 2));
  }

  @Test
  void getOffsetShouldReturnZeroIfZeroValuePassed() {
    utilities.when(() -> Utils.getOffset(-2, 2)).thenCallRealMethod();
    Assertions.assertEquals(0, Utils.getOffset(0, 2));
  }

  @Test
  void getOffsetShouldReturnCalculatedValue() {
    utilities.when(() -> Utils.getOffset(2, 2)).thenCallRealMethod();
    Assertions.assertEquals(2, Utils.getOffset(2, 2));
  }

  @Test
  void validateIdShouldReturnValue() throws ValidationException {
    utilities.when(() -> Utils.retrieveId("252525")).thenCallRealMethod();
    Assertions.assertEquals(252525, Utils.retrieveId("252525"));
  }

  @Test
  void retrieveIdShouldThrowValidationExceptionIfNullInput() {
    utilities.when(() -> Utils.retrieveId(null)).thenCallRealMethod();
    Assertions.assertThrows(ValidationException.class, () -> Utils.retrieveId(null));
  }

  @Test
  void retrieveIdShouldThrowValidationExceptionIfEmptyInput() {
    utilities.when(() -> Utils.retrieveId("")).thenCallRealMethod();
    Assertions.assertThrows(ValidationException.class, () -> Utils.retrieveId(""));
  }

  @ParameterizedTest
  @ValueSource(strings = {"25g", "askjakj", " -+=[])(*&^%$#@!"})
  void retrieveIdShouldThrowValidationExceptionIfInputContainsLetters(String input) {
    utilities.when(() -> Utils.retrieveId(input)).thenCallRealMethod();
    ValidationException ex = Assertions
        .assertThrows(ValidationException.class, () -> Utils.retrieveId(input));
    Assertions.assertEquals(Logs.MUST_BE_A_NUMBER, ex.getMessage());
  }


  @Test
  void getIntShouldReturnValue() throws ValidationException {
    utilities.when(() -> Utils.getInt("252525")).thenCallRealMethod();
    Assertions.assertEquals(252525, Utils.getInt("252525"));
  }

  @Test
  void getIntShouldThrowValidationExceptionIfNullInput() {
    utilities.when(() -> Utils.getInt(null)).thenCallRealMethod();
    Assertions.assertThrows(ValidationException.class, () -> Utils.getInt(null));
  }

  @Test
  void getIntShouldThrowValidationExceptionIfEmptyInput() {
    utilities.when(() -> Utils.getInt("")).thenCallRealMethod();
    Assertions.assertThrows(ValidationException.class, () -> Utils.getInt(""));
  }

  @ParameterizedTest
  @ValueSource(strings = {"25g", "askjakj", " -+=[])(*&^%$#@!"})
  void getIntShouldThrowValidationExceptionIfInputContainsLetters(String input) {
    utilities.when(() -> Utils.getInt(input)).thenCallRealMethod();
    ValidationException ex = Assertions
        .assertThrows(ValidationException.class, () -> Utils.getInt(input));
    Assertions.assertEquals(Logs.MUST_BE_A_NUMBER, ex.getMessage());
  }
}