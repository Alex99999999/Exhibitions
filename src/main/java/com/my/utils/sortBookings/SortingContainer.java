package com.my.utils.sortBookings;

import com.my.exception.ValidationException;
import com.my.utils.constants.Logs;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


public class SortingContainer {

  private static Map<String, Sortable> sortingMap;
  private static final Logger LOG = Logger.getLogger(SortingContainer.class);

  static {
    sortingMap = new HashMap<>();
    sortingMap.put("booking_topic", new BookingTopicSorting());
    sortingMap.put("booking_exhibition_status", new BookingExhibitionStatusSorting());
    sortingMap.put("booking_price", new BookingPriceSorting());
    sortingMap.put("booking_status", new BookingStatusSorting());
    sortingMap.put("booking_user", new BookingUserSorting());
    sortingMap.put("booking_tickets", new BookingTicketQuantitySorting());
  }

  public static Sortable getSortingOption(String key) throws ValidationException {
    if (!sortingMap.containsKey(key)) {
      String errorMes = Logs.NO_SUCH_SORTING_OPTION;
      LOG.warn(errorMes);
      throw new ValidationException(errorMes);
    }
    return sortingMap.get(key);
  }
}