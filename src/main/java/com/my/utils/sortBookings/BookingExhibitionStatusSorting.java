package com.my.utils.sortBookings;

import com.my.entity.Booking;
import java.util.Collections;
import java.util.List;

public class BookingExhibitionStatusSorting implements Sortable<Booking> {

  @Override
  public List<Booking> sort(List<Booking> list) {
    Collections.sort(list,
        (a, b) -> a.getExhibition().getStatus().getStatus().compareToIgnoreCase(b.getExhibition().getStatus().getStatus()));
    return list;
  }
}
