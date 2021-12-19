package com.my.db.dao.currency;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.my.entity.Currency;
import com.my.exception.DBException;
import com.my.utils.DbUtils;
import com.my.utils.constants.Columns;
import com.my.utils.constants.Logs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class CurrencyRepoTest {

  private CurrencyRepo repo;
  private ResultSet rs;
  private Statement stmt;
  private PreparedStatement ps;
  private Connection con;

  private static MockedStatic<CurrencyRepo> mockRepo;
  private static MockedStatic<DbUtils> utils;

  @BeforeClass
  public static void setupGlobal() {
    mockRepo = Mockito.mockStatic(CurrencyRepo.class);
    utils = Mockito.mockStatic(DbUtils.class);
  }

  @AfterClass
  public static void tearDownGlobal() {
    mockRepo.close();
    utils.close();
  }

  @Before
  public void setup() throws SQLException {
    repo = mock(CurrencyRepo.class);
    rs = mock(ResultSet.class);
    stmt = mock(Statement.class);
    ps = mock(PreparedStatement.class);
    con = mock(Connection.class);

    mockRepo.when(CurrencyRepo::getInstance).thenReturn(repo);
    utils.when(DbUtils::getCon).thenReturn(con);

    when(con.createStatement())
        .thenReturn(stmt);

    when(rs.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(50L)
        .thenReturn(100L);

    when(rs.getString(Columns.CURRENCY))
        .thenReturn("UAH")
        .thenReturn("USD");
  }

  @Test
  public void findAllCurrencyShouldReturnList() throws SQLException, DBException {
    when(repo.findAllCurrency())
        .thenCallRealMethod();

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    List<Currency> list = repo.findAllCurrency();

    Assertions.assertEquals(2, list.size());

  }

  @Test
  public void findAllCurrencyShouldThrowDBException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    when(repo.findAllCurrency())
        .thenCallRealMethod();

    DBException e = Assertions.assertThrows(DBException.class, () -> repo.findAllCurrency());
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());
  }

  @Test
  public void findCurrencyByIdShouldReturnCurrency() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(false);
    when(rs.getLong(Columns.ID))
        .thenReturn(50L);
    when(rs.getString(Columns.CURRENCY))
        .thenReturn("UAH");

    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);
    when(ps.executeQuery())
        .thenReturn(rs);
    when(repo.findCurrencyById(50L))
        .thenCallRealMethod();
    Currency cur = repo.findCurrencyById(50L);
    Assertions.assertEquals("UAH", cur.getCurrency());
  }

  @Test
  public void findCurrencyByIdShouldThrowDBException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);
    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);
    when(ps.executeQuery())
        .thenReturn(rs);
    when(repo.findCurrencyById(50L))
        .thenCallRealMethod();

    DBException e = Assertions.assertThrows(DBException.class, () -> repo.findCurrencyById(50L));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());
  }

}
