package com.my.db.dao.hallStatus;

final class Sql {

  static final String FIND_BY_STATUS = "SELECT * FROM hall_status WHERE status = ?";
  static final String FIND_ALL = "SELECT * FROM hall_status";
  static final String FIND_BY_ID = "SELECT * FROM hall_status WHERE id = ?";
}
