package com.my.db.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.my.entity.Status;
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

public class StatusRepoTest {

  private ResultSet rs;
  private Statement stmt;
  private PreparedStatement ps;
  private Connection con;
  private StatusRepo repo;
  private String sqlFindAll = "select * from booking_status";
  private String sqlFindById = "select * from booking_status where id=?";
  private String sqlFindByStatus = "select * from booking_status where status=?";

  private static MockedStatic<DbUtils> mockDbUtils;
  private static MockedStatic<StatusRepo> mockRepo;

  @BeforeClass
  public static void setupGlobal() {
    mockDbUtils = Mockito.mockStatic(DbUtils.class);
    mockRepo = Mockito.mockStatic(StatusRepo.class);
  }

  @AfterClass
  public static void tearDownGlobal() {
    mockDbUtils.close();
    mockRepo.close();
  }

  @Before
  public void setup() throws SQLException {
    rs = mock(ResultSet.class);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(2L);

    when(rs.getString(Columns.STATUS))
        .thenReturn("status");

    stmt = mock(Statement.class);

    ps = mock(PreparedStatement.class);
    when(ps.executeQuery())
        .thenReturn(rs);

    con = mock(Connection.class);

    repo = mock(StatusRepo.class);

    mockDbUtils.when(DbUtils::getCon).thenReturn(con);
    mockRepo.when(StatusRepo::getInstance).thenReturn(repo);
  }

  @Test
  public void findAllShouldReturnList() throws SQLException, DBException {

    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(1L)
        .thenReturn(2L);

    when(rs.getString(Columns.STATUS))
        .thenReturn("status1")
        .thenReturn("status2");

    when(repo.findAll(sqlFindAll))
        .thenCallRealMethod();

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(sqlFindAll))
        .thenReturn(rs);

    List<Status> list = repo.findAll(sqlFindAll);

    Assertions.assertEquals(2, list.size());
  }

  @Test
  public void findAllBookingStatusesShouldThrowDBException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(sqlFindAll))
        .thenReturn(rs);

    when(repo.findAll(sqlFindAll))
        .thenCallRealMethod();

    DBException ex = Assertions
        .assertThrows(DBException.class, () -> repo.findAll(sqlFindAll));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, ex.getMessage());
  }

  @Test
  public void findStatusByIdShouldReturnResult() throws SQLException, DBException {
    when(con.prepareStatement(sqlFindById))
        .thenReturn(ps);

    when(repo.findById(sqlFindById, 2L))
        .thenCallRealMethod();

    Status status = repo.findById(sqlFindById, 2L);
    Assertions.assertEquals("status", status.getStatus());
  }

  @Test
  public void findStatusByIdShouldThrowDbException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.prepareStatement(sqlFindById))
        .thenReturn(ps);

    when(repo.findById(sqlFindById, 2L))
        .thenCallRealMethod();

    DBException e = Assertions
        .assertThrows(DBException.class, () -> repo.findById(sqlFindById, 2L));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());
  }

  @Test
  public void findByStringShouldReturnResult() throws SQLException, DBException {
    when(con.prepareStatement(sqlFindByStatus))
        .thenReturn(ps);

    when(repo.findByString(sqlFindByStatus, "status"))
        .thenCallRealMethod();

    Status status = repo.findByString(sqlFindByStatus, "status");
    Assertions.assertEquals("status", status.getStatus());
  }

  @Test
  public void findBStringShouldThrowDbException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.prepareStatement(sqlFindByStatus))
        .thenReturn(ps);

    when(repo.findByString(sqlFindByStatus, "status"))
        .thenCallRealMethod();

    DBException e = Assertions
        .assertThrows(DBException.class, () -> repo.findByString(sqlFindByStatus, "status"));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());
  }
}