package com.my.utils.sortBookings;

import com.my.entity.Booking;
import com.my.entity.Exhibition;
import com.my.entity.ExhibitionStatus;
import com.my.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BookingExhibitionUserSortingTest {

  private static List<Booking> list = new ArrayList<>();


  @BeforeClass
  public static void setup() {
    User user = User.getInstance();
    user.setLogin("Bob");
    Booking booking1 = Booking.getInstance();
    booking1.setUser(user);

    list.add(booking1);

    user = User.getInstance();
    user.setLogin("Archy");
    Booking booking2 = Booking.getInstance();
    booking2.setUser(user);
    list.add(booking2);
  }


  @Test
  public void ExhibitionStatusSortingShouldSort() {
    Sortable sortable = new BookingUserSorting();
    List<Booking> sorted = sortable.sort(list);
    Assertions.assertEquals("Archy", sorted.get(0).getUser().getLogin());
  }
}
