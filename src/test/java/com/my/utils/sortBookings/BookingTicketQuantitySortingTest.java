package com.my.utils.sortBookings;

import com.my.entity.Booking;
import com.my.entity.Exhibition;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BookingTicketQuantitySortingTest {

  private static List<Booking> list = new ArrayList<>();
  private static Exhibition exhibition;
  private static Booking booking1;
  private static Booking booking2;


  @BeforeClass
  public static void setup() {

    booking1 = Booking.getInstance();
    booking1.setTicketQty(2);

    list.add(booking1);

    booking2 = Booking.getInstance();
    booking2.setTicketQty(1);
    list.add(booking2);
  }

  @Test
  public void ExhibitionStatusSortingShouldSort() {
    Sortable sortable = new BookingTicketQuantitySorting();
    List<Booking> sorted = sortable.sort(list);
    Assertions.assertEquals(1, sorted.get(0).getTicketQty());
  }
}
