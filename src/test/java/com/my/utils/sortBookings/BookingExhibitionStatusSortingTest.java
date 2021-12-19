package com.my.utils.sortBookings;

import com.my.entity.Booking;
import com.my.entity.Exhibition;
import com.my.entity.Status;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BookingExhibitionStatusSortingTest {

  private static List<Booking> list = new ArrayList<>();
  private static Status status;
  private static Exhibition exhibition;
  private static Booking booking1;
  private static Booking booking2;


  @BeforeClass
  public static void setup() {
    status = Status.getInstance();
    status.setStatus("pending");
    exhibition = Exhibition.getInstance();
    exhibition.setStatus(status);
    booking1 = Booking.getInstance();
    booking1.setExhibition(exhibition);

    list.add(booking1);

    status = Status.getInstance();
    status.setStatus("current");
    exhibition = Exhibition.getInstance();
    exhibition.setStatus(status);
    booking2 = Booking.getInstance();
    booking2.setExhibition(exhibition);
    list.add(booking2);
  }


  @Test
  public void ExhibitionStatusSortingShouldSort() {
    Sortable sortable = new BookingExhibitionStatusSorting();
    List<Booking> sorted = sortable.sort(list);
    Assertions.assertEquals("current", sorted.get(0).getExhibition().getStatus().getStatus());
  }
}
