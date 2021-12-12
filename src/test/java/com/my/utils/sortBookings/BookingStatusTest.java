package com.my.utils.sortBookings;

import com.my.entity.Booking;
import com.my.entity.BookingStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BookingStatusTest {

  private static List<Booking> list = new ArrayList<>();
  private static BookingStatus status;
  private static Booking booking1;
  private static Booking booking2;


  @BeforeClass
  public static void setup() {
    status = BookingStatus.getInstance();
    status.setStatus("paid");
    booking1 = Booking.getInstance();
    booking1.setStatus(status);

    list.add(booking1);

    status = BookingStatus.getInstance();
    status.setStatus("booked");
    booking2 = Booking.getInstance();
    booking2.setStatus(status);
    list.add(booking2);
  }

  @Test
  public void ExhibitionStatusSortingShouldSort() {
    Sortable sortable = new BookingStatusSorting();
    List<Booking> sorted = sortable.sort(list);
    Assertions.assertEquals("booked", sorted.get(0).getStatus().getStatus());
  }
}