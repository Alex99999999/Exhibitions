package com.my.db.dao.user;

final class Sql {

  static final String SORT_ALL_BY_COLUMN_DESC = "SELECT * FROM user ORDER BY %s desc LIMIT ? OFFSET ?";;
  static final String SORT_ALL_BY_COLUMN = "SELECT * FROM user ORDER BY %s LIMIT ? OFFSET ?";
  static final String GET_BY_LOGIN_LIKE = "SELECT * FROM user WHERE login LIKE ?";
  static final String GET_COUNT_BY_ROLE = "SELECT count(*) FROM user WHERE user_roles_id=(SELECT id FROM user_role WHERE role = ?)";
  static final String FIND_USERS_LIMIT_OFFSET = "SELECT * FROM user LIMIT ? OFFSET ? ";
  static final String FIND_USERS_BY_ROLE_LIMIT_OFFSET = "SELECT * FROM user WHERE user_roles_id=(SELECT id FROM user_role WHERE role = ?) LIMIT ? OFFSET ? ";
  static final String GET_ROLE = "SELECT * FROM user_role WHERE id = ?";
  static final String GET_COUNT = "SELECT count(*) FROM user";
  static final String FIND_ALL = "SELECT * FROM user;";
  static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
  static final String FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
  static final String CREATE_USER = "INSERT into user (id, login, password, user_roles_id) "
      + "VALUES (DEFAULT, ?, ?, (SELECT id from user_role where role = ?));";

  static final String UPDATE_USER = "UPDATE user SET login = ?,  password = ?, user_roles_id= ? WHERE id = ?;";
  static final String DELETE_BY_ID = "DELETE from user WHERE id = ?";

}
