package com.my.utils.sortBookings;

import com.my.entity.Booking;
import com.my.entity.Exhibition;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BookingTopicSortingTest {

  private static List<Booking> list = new ArrayList<>();
  private static Exhibition exhibition;
  private static Booking booking1;
  private static Booking booking2;


  @BeforeClass
  public static void setup() {

    exhibition = Exhibition.getInstance();
    exhibition.setTopic("Books");
    booking1 = Booking.getInstance();
    booking1.setExhibition(exhibition);

    list.add(booking1);


    exhibition = Exhibition.getInstance();
    exhibition.setTopic("Authors");
    booking2 = Booking.getInstance();
    booking2.setExhibition(exhibition);
    list.add(booking2);
  }

  @Test
  public void ExhibitionStatusSortingShouldSort() {
    Sortable sortable = new BookingTopicSorting();
    List<Booking> sorted = sortable.sort(list);
    Assertions.assertEquals("Authors", sorted.get(0).getExhibition().getTopic());
  }
}