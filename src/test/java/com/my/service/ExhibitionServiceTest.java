package com.my.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.my.entity.Exhibition;
import com.my.exception.DateFormatException;
import com.my.exception.ServiceException;
import com.my.exception.ValidationException;
import com.my.utils.Utils;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

class ExhibitionServiceTest {

  private static HttpServletRequest mockRequest;
  private static ExhibitionService service;
  private static Exhibition instance;
  private static MockedStatic<Utils> utilities;


  @BeforeAll
  private static void setup() {
    mockRequest = mock(HttpServletRequest.class);
    service = ExhibitionService.getInstance();
    instance = Exhibition.getInstance();
    utilities = mockStatic(Utils.class);
  }

  @AfterAll
  public static void tearDown() {
    utilities .close();
  }

  @Test
  void getInstanceShouldReturnSameInstance() {
    ExhibitionService inst = ExhibitionService.getInstance();
    Assertions.assertEquals(inst, service);
  }

  @Test
  void validateDateAndTimeShouldNotThrowValidationException() {
    when(mockRequest.getParameter(Params.START_DATE)).thenReturn("2021-11-30");
    when(mockRequest.getParameter(Params.END_DATE)).thenReturn("2021-12-30");
    when(mockRequest.getParameter(Params.START_TIME)).thenReturn("08:00");
    when(mockRequest.getParameter(Params.END_TIME)).thenReturn("18:00");
    Assertions
        .assertDoesNotThrow(() -> service.verifyBeginningBeforeEnd(mockRequest));
  }

  @Test
  void validateDateAndTimeShouldThrowValidationExceptionIfEndDateBeforeStartDate() {
    when(mockRequest.getParameter(Params.START_DATE)).thenReturn("2021-11-30");
    when(mockRequest.getParameter(Params.END_DATE)).thenReturn("2021-10-30");
    when(mockRequest.getParameter(Params.START_TIME)).thenReturn("08:00");
    when(mockRequest.getParameter(Params.END_TIME)).thenReturn("18:00");

    ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> {
      service.verifyBeginningBeforeEnd(mockRequest);
    });
    Assertions.assertEquals("Invalid date range", ex.getMessage());
  }

  @Test
  void validateDateAndTimeShouldThrowValidationExceptionIfStartTineAfterEndTime() {
    when(mockRequest.getParameter(Params.START_DATE)).thenReturn("2021-11-30");
    when(mockRequest.getParameter(Params.END_DATE)).thenReturn("2021-12-30");
    when(mockRequest.getParameter(Params.START_TIME)).thenReturn("08:00");
    when(mockRequest.getParameter(Params.END_TIME)).thenReturn("06:00");

    ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> {
      service.verifyBeginningBeforeEnd(mockRequest);
    });
    Assertions.assertEquals("Invalid time range", ex.getMessage());
  }

  @Test
  void validateInputShouldNotThrowExceptionIfDescriptionIsNull() {
    when(mockRequest.getParameter(Params.EXHIBITION_ID)).thenReturn("152");
    when(mockRequest.getParameter(Params.TOPIC)).thenReturn("Test");
    when(mockRequest.getParameter(Params.DESCRIPTION)).thenReturn(null);
    when(mockRequest.getParameter(Params.START_DATE)).thenReturn("2021-11-30");
    when(mockRequest.getParameter(Params.END_DATE)).thenReturn("2021-12-30");
    when(mockRequest.getParameter(Params.START_TIME)).thenReturn("08:00");
    when(mockRequest.getParameter(Params.END_TIME)).thenReturn("10:00");
    when(mockRequest.getParameter(Params.PRICE)).thenReturn("652.2");
    when(mockRequest.getParameter(Params.TICKETS_AVAILABLE)).thenReturn("100");
    when(mockRequest.getParameter(Params.STATUS)).thenReturn("CURRENT");
    when(mockRequest.getParameter(Params.CURRENCY)).thenReturn("UAH");

    Assertions.assertDoesNotThrow(() -> service.validateInput(mockRequest));
  }

  @Test
  void setTicketQuantityShouldIncrease() throws ServiceException {
    instance.setTicketsAvailable(5);
    int booked = 10;
    String action = "increase";
    Exhibition ex = service.setTicketQuantity(instance, booked, action);
    Assertions.assertEquals(15, ex.getTicketsAvailable());
  }

  @Test
  void setTicketQuantityShouldDecrease() throws ServiceException {
    instance.setTicketsAvailable(5);
    int booked = 2;
    String action = "";
    Exhibition ex = service.setTicketQuantity(instance, booked, action);
    Assertions.assertEquals(3, ex.getTicketsAvailable());
  }

  @Test
  void setTicketQuantityShouldThrowServiceException() {
    instance.setTicketsAvailable(5);
    int booked = 10;
    String action = "";
    ServiceException ex = Assertions.assertThrows(ServiceException.class,
        () -> service.setTicketQuantity(instance, booked, action));

    Assertions.assertEquals("Impossible to book 10 tickets", ex.getMessage());
  }

  @Test
  void DateDisplayFormatShouldReturnCorrectDateIfExpectedInput() throws ValidationException {
    String date = "2021-12-23";
    String expected = "Dec 23, 2021";
    String actual = service.dateDisplayFormat(date);
    Assertions.assertEquals(expected, actual);
  }

  // There is an issue with those dates which begin with 0.
  // While parsing LocalDate eliminates 0 which may be the cause of error.
  @Test
  void DateDisplayFormatShouldReturnCorrectDateIfDayIsOneDigitValue() throws ValidationException {
    String date = "2021-12-03";
    String expected = "Dec 03, 2021";
    String actual = service.dateDisplayFormat(date);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void DateDisplayFormatShouldThrowValidationExceptionIfInputIsNull() {
    ValidationException ex = Assertions
        .assertThrows(ValidationException.class, () -> service.dateDisplayFormat(null));
    Assertions.assertEquals(Logs.NULL_NOT_ALLOWED, ex.getMessage());
  }

  @Test
  void TimeDisplayFormatShouldReturnCorrectTimeIfExpectedInput() throws ValidationException {
    String date = "08:00:00";
    String expected = "08:00";
    String actual = service.timeDisplayFormat(date);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void TimeDisplayFormatShouldReturnCorrectTimeIfOneDigitValueForMinAndSec()
      throws ValidationException {
    String date = "08:05:05";
    String expected = "08:05";
    String actual = service.timeDisplayFormat(date);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void TimeDisplayFormatShouldThrowValidationExceptionIfInputIsNull() {
    ValidationException ex = Assertions
        .assertThrows(ValidationException.class, () -> service.timeDisplayFormat(null));
    Assertions.assertEquals(Logs.NULL_NOT_ALLOWED, ex.getMessage());
  }

  @Test
  void toDbDateFormatShouldReturnDbDateFormat() throws ValidationException, DateFormatException {
    String date = "Dec 03, 2021";
    String expected = "2021-12-03";
    String actual = service.toDbDateFormat(date);
    Assertions.assertEquals(actual, expected);
  }

  @Test
  void toDbDateFormatShouldThrowValidationException() {
    ValidationException ex = Assertions
        .assertThrows(ValidationException.class, () -> service.toDbDateFormat(null));
    Assertions.assertEquals(Logs.NULL_NOT_ALLOWED, ex.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"December 05, 2021", "December, 05 2021", "05 December, 2021",
      "05 Dec, 2021", "25 DEC, 2021", "05 DECEMBER, 2021", "DECEMBER 05, 2021",
      "DEC 5, 2021", "5 DEC, 2021", "5 DECEMBER, 2021", "DECEMBER 5, 2021", "5 DEC 2021",
      "DEC 5 2021", "05-Dec-2021"})
  void toDbDateFormatShouldThrowDateFormatException(String input) {
    DateFormatException ex = Assertions
        .assertThrows(DateFormatException.class, () -> service.toDbDateFormat(input));
    Assertions.assertEquals(Logs.WRONG_DATE_FORMAT, ex.getMessage());
  }
}
