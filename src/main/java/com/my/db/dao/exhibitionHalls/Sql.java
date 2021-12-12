package com.my.db.dao.exhibitionHalls;

final class Sql {

  static final String FIND_BY_EXHIBITION_ID = "SELECT count(*) from exhibition_has_hall WHERE exhibition_id = ?";
  static final String SET_HALL_FOR_EXHIBITION = "INSERT into exhibition_has_hall (exhibition_id, hall_id) VALUES (?, ?)";
  static final String DELETE_HALL_FROM_EXHIBITION = "DELETE from exhibition_has_hall where hall_id = ?";
  static final String GET_HALLS_BY_EXHIBITION_ID = "SELECT hall_id FROM exhibition_has_hall WHERE  exhibition_id = ? ";

}
