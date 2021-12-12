package com.my.db.dao.role;

final class Sql {

  static final String FIND_ALL = "SELECT * FROM user_role";
  static final String FIND_BY_ID = "SELECT * FROM user_role WHERE id = ?";
  static final String FIND_BY_ROLE = "SELECT * FROM user_role WHERE role = ?";
  static final String INSERT_ROLE = "INSERT into user_role (id, role) VALUES (DEFAULT, ?)";
  static final String DELETE_BY_ID = "DELETE from user_role WHERE id = ?";

}
