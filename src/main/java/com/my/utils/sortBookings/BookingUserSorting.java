package com.my.utils.sortBookings;

import com.my.entity.Booking;
import java.util.Collections;
import java.util.List;

public class BookingUserSorting implements Sortable<Booking> {

  @Override
  public List<Booking> sort(List<Booking> list) {
    Collections.sort(list,
        (a, b) -> a.getUser().getLogin().compareToIgnoreCase(b.getUser().getLogin()));
    return list;
  }
}
