package com.my.db.dao.bookingStatus;

import com.my.entity.BookingStatus;
import com.my.exception.DBException;
import java.util.List;

public class BookingStatusDao {

  private static BookingStatusDao instance;
  private static final BookingStatusRepo BOOKING_STATUS_REPO  = BookingStatusRepo.getInstance();

  public static synchronized BookingStatusDao getInstance() {
    if (instance == null) {
      instance = new BookingStatusDao();
    }
    return instance;
  }

  public List<BookingStatus> findAll() throws DBException {
    return BOOKING_STATUS_REPO.findAllBookingStatuses();
  }

  public BookingStatus findById(long id) throws DBException {
    return BOOKING_STATUS_REPO.findStatusById(id);
  }
}
