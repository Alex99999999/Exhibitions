package com.my.utils.sortBookings;

import com.my.entity.Booking;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingTicketQuantitySorting implements Sortable<Booking> {

  @Override
  public List<Booking> sort(List<Booking> list) {
    Collections.sort(list, Comparator.comparingInt(Booking::getTicketQty));
    return list;
  }
}
