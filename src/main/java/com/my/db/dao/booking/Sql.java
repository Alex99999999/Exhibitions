package com.my.db.dao.booking;

public class Sql {

  static final String FIND_BY_EXHIBITION_ID_AND_BOOKING_STATUS = "SELECT count(*) from booking where exhibition_id = ? AND (status_id=(SELECT id from booking_status WHERE status = ?) OR status_id=(SELECT id from booking_status WHERE status = ?))";
  static final String GET_ALL_BOOKING_COUNT = "SELECT count(*) FROM booking";
  static final String FIND_ALL_OFFSET_LIMIT = "SELECT * FROM booking LIMIT ? OFFSET ?;";
  static final String FIND_ALL_BY_STATUS_OFFSET_LIMIT_NO_FILTER = "SELECT * FROM booking WHERE status_id = (SELECT id FROM booking_status where status = ?) LIMIT ? OFFSET ?";
  static final String FIND_ALL_BY_LOGIN_OFFSET_LIMIT_NO_FILTER = "SELECT * FROM booking WHERE user_id = (SELECT id FROM user where login = ?) LIMIT ? OFFSET ?";
  static final String FIND_ALL_BY_LOGIN_AND_STATUS_OFFSET_LIMIT_NO_FILTER = "SELECT * FROM booking WHERE status_id = (SELECT id FROM booking_status where status = ?) AND user_id = (SELECT id from user WHERE login = ?) limit ? offset ?";
  static final String DELETE_BOOKING_BY_ID = "DELETE FROM booking WHERE id = ?";
  static final String INSERT = "INSERT into booking (id, ticket_qty, exhibition_id, user_id, status_id) VALUES (DEFAULT, ?, ?, ?, (select id from booking_status where status = ?));";
  static final String FIND_BY_USER_ID = "SELECT * FROM booking WHERE user_id = ? "
      + "AND status_id = (SELECT id FROM booking_status WHERE status = 'PAID')"
      + "OR status_id = (SELECT id FROM booking_status WHERE status = 'BOOKED')";
  static final String FIND_ALL = "SELECT * FROM booking";
  static final String FIND_BY_ID = "SELECT * FROM booking WHERE id = ?";
  static final String UPDATE_BOOKING = "UPDATE booking SET ticket_qty = ?, status_id=(SELECT id from booking_status where status=?) where id=?;";

}
