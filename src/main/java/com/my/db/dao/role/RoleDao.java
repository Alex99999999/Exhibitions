package com.my.db.dao.role;

import com.my.db.dao.Dao;
import com.my.entity.UserRole;
import com.my.exception.DBException;
import java.util.List;
import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class RoleDao implements Dao {

  private static RoleDao instance;
  private static final RoleRepo ROLE_REPO = RoleRepo.getInstance();

  public static synchronized RoleDao getInstance() {
    if (instance == null) {
      instance = new RoleDao();
    }
    return instance;
  }

  @Override
  public List<UserRole> findAll() throws DBException {
    return ROLE_REPO.getAllRoles();
  }

  @Override
  public UserRole findById(long id) throws DBException {
    return ROLE_REPO.getById(id);
  }

  @Override
  public void update(HttpServletRequest req) throws DBException {
    ROLE_REPO.updateRole(req);
  }

  @Override
  public void create(HttpServletRequest req) throws DBException {
    ROLE_REPO.createRole(req);
  }

  @Override
  public void delete(long id) throws DBException {
    ROLE_REPO.deleteRole(id);
  }

  public UserRole findByUserRole(String role) throws DBException {
    return ROLE_REPO.getByRole(role);
  }

  public boolean isExist(String role) throws DBException {
    return ROLE_REPO.isStored(role);

  }
}
