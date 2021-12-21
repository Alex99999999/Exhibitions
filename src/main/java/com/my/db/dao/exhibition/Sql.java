package com.my.db.dao.exhibition;

final class Sql {

  static final String FIND_CURRENT_OFFSET_LIMIT_BY_TOPIC = "SELECT * FROM exhibition WHERE status_id=(SELECT id FROM exhibition_status WHERE status = 'current') and topic LIKE ? LIMIT ? OFFSET ?;";
  static final String FILTER_CURRENT_BY_DATE = "SELECT * FROM exhibition WHERE start_date < ? and end_date > ? and status_id=(SELECT id from exhibition_status WHERE status = 'current') LIMIT ? OFFSET ?";
  static final String FILTER_ALL_BY_DATE = "SELECT * FROM exhibition WHERE start_date < ? and end_date > ? LIMIT ? OFFSET ?;";
  static final String FIND_ALL_LIMIT_OFFSET_ORDER = "SELECT * FROM exhibition ORDER BY %s LIMIT ? OFFSET ?;";
  static final String FIND_ALL_LIMIT_OFFSET_ORDER_DESC = "SELECT * FROM exhibition ORDER BY %s desc LIMIT ? OFFSET ?;";
  static final String FIND_CURRENT_LIMIT_OFFSET_ORDER = "SELECT * FROM exhibition WHERE status_id=(SELECT id FROM exhibition_status WHERE status = 'current') ORDER BY %s LIMIT ? OFFSET ?;";
  static final String FIND_CURRENT_LIMIT_OFFSET_ORDER_DESC =  "SELECT * FROM exhibition WHERE status_id=(SELECT id FROM exhibition_status WHERE status = 'current') ORDER BY %s desc LIMIT ? OFFSET ?;";

  static final String GET_ALL_EXHIBITION_COUNT = "SELECT count(*) FROM exhibition";
  static final String GET_CURRENT_EXHIBITION_COUNT = "SELECT count(*) FROM exhibition where status_id=(SELECT id FROM exhibition_status WHERE status = 'current')";
  static final String FIND_CURRENT_OFFSET_LIMIT_NO_FILTER = "SELECT * FROM exhibition WHERE status_id=(SELECT id FROM exhibition_status WHERE status = 'current') LIMIT ? OFFSET ?;";
  static final String FIND_ALL_OFFSET_LIMIT_NO_FILTER = "SELECT * FROM exhibition LIMIT ? OFFSET ?;";
  static final String CREATE_EXHIBITION = "INSERT into exhibition (id, topic, start_date, end_date, start_time, end_time, price, tickets_available, status_id, currency_id, description) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, (SELECT id from exhibition_status WHERE status = ?), (SELECT id from currency WHERE currency = ?), ?);";
  static final String FIND_ALL = "SELECT * FROM exhibition";
  static final String FIND_BY_ID = "SELECT * FROM exhibition WHERE id = ?";
  static final String FIND_BY_TOPIC_LIMIT_OFFSET = "SELECT * FROM exhibition WHERE topic LIKE ? LIMIT ? OFFSET ?;";
  static final String UPDATE_EXHIBITION =
      "UPDATE exhibition SET topic = ?, start_date = ?, end_date = ?, start_time = ?, end_time = ?, price = ?, tickets_available = ?,"
          + "status_id = (SELECT id FROM exhibition_status WHERE status = ?), currency_id = (SELECT id FROM currency WHERE currency = ?), description = ? WHERE id = ?;";
  static final String DELETE_BY_ID = "DELETE from exhibition WHERE id = ?";

}
