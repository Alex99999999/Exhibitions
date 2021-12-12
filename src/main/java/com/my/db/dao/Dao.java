package com.my.db.dao;

import com.my.entity.Exhibition;
import com.my.entity.User;
import com.my.exception.DBException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface Dao<T> {

  List<T> findAll() throws DBException;

  T findById(long id) throws DBException;

  void update(HttpServletRequest req) throws DBException;

  void create(HttpServletRequest req) throws DBException;

  void delete(long id) throws DBException;


}
