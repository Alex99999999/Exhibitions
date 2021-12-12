package com.my.db.dao.exhibitionStatus;

final class Sql {

  static final String GET_ID = "SELECT id FROM exhibition_status WHERE status = ?";
  static final String FIND_ALL = "SELECT * FROM exhibition_status";
  static final String FIND_BY_ID = "SELECT * FROM exhibition_status WHERE id = ?";
}
