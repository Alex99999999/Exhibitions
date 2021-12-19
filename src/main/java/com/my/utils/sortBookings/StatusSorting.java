package com.my.utils.sortBookings;

import com.my.entity.Booking;
import java.util.Collections;
import java.util.List;

public class StatusSorting implements Sortable<Booking> {

  @Override
  public List<Booking> sort(List<Booking> list) {

    Collections.sort(list,
        (a, b) -> a.getStatus().getStatus().compareToIgnoreCase(b.getStatus().getStatus()));
    return list;
  }
}
