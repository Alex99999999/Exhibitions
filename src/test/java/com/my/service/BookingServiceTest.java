package com.my.service;

import static org.mockito.Mockito.*;

import com.my.exception.ServiceException;
import com.my.utils.constants.Params;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BookingServiceTest {

  private static HttpServletRequest mockRequest;
  private static BookingService service;

  @BeforeAll
  private static void setup() {
    mockRequest = mock(HttpServletRequest.class);
    service = BookingService.getInstance();
  }

  @Test
  void getInstanceShouldReturnSameInstance() {
    BookingService inst = BookingService.getInstance();
    Assertions.assertEquals(inst, service);
  }

  @Test
  void verifyTicketsQuantityDoesNotThrowException() {
    when(mockRequest.getParameter(Params.TICKETS_BOOKED)).thenReturn("15");
    Assertions
        .assertDoesNotThrow(() -> BookingService.getInstance().verifyTicketsQuantity(mockRequest));
  }

  @Test
  void verifyTicketsQuantityThrowsException() {
    when(mockRequest.getParameter(Params.TICKETS_BOOKED)).thenReturn("0");
    ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> {
      BookingService.getInstance().verifyTicketsQuantity(mockRequest);
    });
    Assertions.assertEquals("Tickets quantity may not be less than 1", ex.getMessage());
  }
}