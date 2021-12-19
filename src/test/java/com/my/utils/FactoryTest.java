package com.my.utils;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.my.db.dao.role.RoleDao;
import com.my.entity.Currency;
import com.my.entity.Status;
import com.my.entity.UserRole;
import com.my.exception.DBException;
import com.my.utils.constants.Columns;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class FactoryTest {

  private ResultSet rs = mock(ResultSet.class);
  private Factory factory = Factory.getInstance();
  private static MockedStatic<RoleDao> roleDaoMock;

  @BeforeClass
  static void setupGlobal() {
    roleDaoMock = Mockito.mockStatic(RoleDao.class);
  }


  @Test
  void createUserRoleShouldReturnInstance() throws SQLException, DBException {
    when(rs.getLong(Columns.ID)).thenReturn(152L);
    when(rs.getString(Columns.ROLE)).thenReturn("user");
    UserRole userRole = factory.createUserRole(rs);
    Assertions.assertNotNull(userRole);
    Assertions.assertEquals("user", userRole.getRole());
    Assertions.assertEquals(152, userRole.getId());
  }

  @Test
  void createUserExhibitionStatusInstance() throws SQLException, DBException {
    when(rs.getLong(Columns.ID)).thenReturn(152L);
    when(rs.getString(Columns.STATUS)).thenReturn("current");
    Status status = factory.createStatus(rs);
    Assertions.assertNotNull(status);
    Assertions.assertEquals("current", status.getStatus());
    Assertions.assertEquals(152, status.getId());
  }

  @Test
  void createCurrencyStatusInstance() throws SQLException, DBException {
    when(rs.getLong(Columns.ID)).thenReturn(152L);
    when(rs.getString(Columns.CURRENCY)).thenReturn("USD");
    Currency currency = factory.createCurrency(rs);
    Assertions.assertNotNull(currency);
    Assertions.assertEquals("USD", currency.getCurrency());
    Assertions.assertEquals(152, currency.getId());
  }

  @Test
  void createHallStatusInstance() throws SQLException, DBException {
    when(rs.getLong(Columns.ID)).thenReturn(152L);
    when(rs.getString(Columns.STATUS)).thenReturn("free");
    Status status = factory.createStatus(rs);
    Assertions.assertNotNull(status);
    Assertions.assertEquals("free", status.getStatus());
    Assertions.assertEquals(152, status.getId());
  }

  @Test
  void createBookingStatusInstance() throws SQLException, DBException {
    when(rs.getLong(Columns.ID)).thenReturn(152L);
    when(rs.getString(Columns.STATUS)).thenReturn("paid");
    Status status = factory.createStatus(rs);
    Assertions.assertNotNull(status);
    Assertions.assertEquals("paid", status.getStatus());
    Assertions.assertEquals(152, status.getId());
  }
}




