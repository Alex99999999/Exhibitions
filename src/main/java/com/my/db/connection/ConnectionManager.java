package com.my.db.connection;

import com.my.exception.DBException;
import java.sql.Connection;

public interface ConnectionManager {

  Connection getConnection() throws DBException;

}
