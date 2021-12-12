package com.my.db.dao.hall;

final class Sql {

  //order
  static final String FIND_ALL_LIMIT_OFFSET_ORDER = "SELECT * FROM hall ORDER BY %s LIMIT ? offset ?";
  static final String FIND_ALL_LIMIT_OFFSET_ORDER_DESC = "SELECT * FROM hall ORDER BY %s DESC LIMIT ? offset ?";
  static final String FIND_HALL_BY_NUMBER = "SELECT * FROM hall WHERE hall_no=?";
  static final String INSERT_HALL = "INSERT into hall (id, floor, floor_space, hall_no, status_id) VALUES (DEFAULT, ?, ?, ?, (SELECT id from hall_status WHERE status = ?));";
  static final String FIND_OFFSET_LIMIT_BY_STATUS_STRING = "SELECT * FROM hall WHERE status_id=(SELECT id from hall_status where status=?) LIMIT ? offset ?;";
  static final String FIND_OFFSET_LIMIT_WHERE_SPACE_GREATER = "SELECT * FROM hall WHERE floor_space > ? LIMIT ? offset ?;";
  static final String FIND_OFFSET_LIMIT_WHERE_SPACE_SMALLER = "SELECT * FROM hall WHERE floor_space < ? LIMIT ? offset ?;";
  static final String FIND_GREATER_OFFSET_LIMIT_BY_STATUS = "SELECT * FROM hall WHERE status_id=(SELECT id from hall_status where status=?) AND floor_space > ? LIMIT ? offset ?;";
  static final String FIND_SMALLER_OFFSET_LIMIT_BY_STATUS = "SELECT * FROM hall WHERE status_id=(SELECT id from hall_status where status=?) AND floor_space < ? LIMIT ? offset ?;";
  static final String GET_ALL_LIMIT_OFFSET = "SELECT * FROM hall LIMIT ? offset ?;";
  static final String FIND_BY_STATUS = "SELECT * FROM hall WHERE status_id = (SELECT id FROM hall_status WHERE status = ?)";
  static final String GET_COUNT = "select count(*) from hall";
  static final String FIND_ALL = "SELECT * FROM hall";
  static final String FIND_BY_ID = "SELECT * FROM hall WHERE id = ?";
  static final String FIND_HALL_ID = "SELECT id FROM hall WHERE hall_no = ?";

  static final String CREATE_HALL = "INSERT into hall (id, floor, floor_space, hall_no, status_id) VALUES (DEFAULT, ?, ?, ?, ?)";

  static final String UPDATE_HALL = "UPDATE hall SET floor = ?,  floor_space =  ?, hall_no = ?,  status_id = (SELECT id from hall_status where status = ?) WHERE id = ?";
  static final String DELETE_BY_ID = "DELETE from hall WHERE id = ?";

}
