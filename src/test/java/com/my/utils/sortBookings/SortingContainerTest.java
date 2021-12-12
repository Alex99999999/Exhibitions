package com.my.utils.sortBookings;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.my.command.Command;
import com.my.command.CommandContainer;
import com.my.entity.Booking;
import com.my.exception.CommandException;
import com.my.exception.ValidationException;
import com.my.utils.constants.Logs;
import com.my.utils.constants.Params;
import com.my.web.command.exhibition.GetExhibitionByTopic;
import com.my.web.command.exhibition.SortExhibitionByStringValue;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class SortingContainerTest {

  private static String key;
  private static Map<String, Sortable> map;

  @BeforeAll
  static void setUp() {
    map = new HashMap<>();
    key = "booking_user";
    map.put(key, new BookingUserSorting());

  }

  @Test
  void getCommandShouldReturnNullIfCommandDoesNotExist()
      throws ValidationException {

    try (MockedStatic<SortingContainer> container = Mockito.mockStatic(SortingContainer.class)) {

      container.when(() -> SortingContainer.getSortingOption(key))
          .thenCallRealMethod();

      Assertions.assertNotNull(SortingContainer.getSortingOption(key));
      Assertions
          .assertInstanceOf(BookingUserSorting.class, SortingContainer.getSortingOption(key));
    }
  }


  @Test
  void getCommandShouldReturnCommandIfAny() {

    try (MockedStatic<SortingContainer> container = Mockito.mockStatic(SortingContainer.class)) {

      container.when(() -> SortingContainer.getSortingOption("no_such_command"))
          .thenCallRealMethod();

      ValidationException ex = Assertions
          .assertThrows(ValidationException.class,
              () -> SortingContainer.getSortingOption("no_such_command"));
      Assertions.assertEquals(Logs.NO_SUCH_SORTING_OPTION, ex.getMessage());
    }
  }
}
