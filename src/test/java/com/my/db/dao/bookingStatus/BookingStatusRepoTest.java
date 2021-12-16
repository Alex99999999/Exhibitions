package com.my.db.dao.bookingStatus;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.my.entity.BookingStatus;
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

public class BookingStatusRepoTest {

 private ResultSet rs;
 private Statement stmt;
 private PreparedStatement ps;
 private Connection con;
 private BookingStatusRepo repo;

  private static MockedStatic<DbUtils> mockDbUtils;
  private static MockedStatic<BookingStatusRepo> mockRepo;

  @BeforeClass
  public static void setupGlobal() {
    mockDbUtils = Mockito.mockStatic(DbUtils.class);
    mockRepo = Mockito.mockStatic(BookingStatusRepo.class);
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
        .thenReturn("booked");

    stmt = mock(Statement.class);

    ps = mock(PreparedStatement.class);
    when(ps.executeQuery())
        .thenReturn(rs);

    con = mock(Connection.class);

    repo = mock(BookingStatusRepo.class);

    mockDbUtils.when(DbUtils::getCon).thenReturn(con);
    mockRepo.when(BookingStatusRepo::getInstance).thenReturn(repo);
  }

  @Test
  public void findAllBookingStatusesShouldReturnList() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(1L)
        .thenReturn(2L);

    when(rs.getString(Columns.STATUS))
        .thenReturn("paid")
        .thenReturn("booked");

    when(repo.findAllBookingStatuses())
        .thenCallRealMethod();

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    List<BookingStatus> list = repo.findAllBookingStatuses();

    Assertions.assertEquals(2, list.size());
  }

  @Test
  public void findAllBookingStatusesShouldThrowDBException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    when(repo.findAllBookingStatuses())
        .thenCallRealMethod();

    DBException ex = Assertions
        .assertThrows(DBException.class, () -> repo.findAllBookingStatuses());
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, ex.getMessage());
  }

  @Test
  public void findStatusByIdShouldReturnResult() throws SQLException, DBException {
    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);

    when(repo.findStatusById(2L))
        .thenCallRealMethod();

    BookingStatus status = repo.findStatusById(2L);
    Assertions.assertEquals("booked", status.getStatus());
  }

  @Test
  public void findStatusByIdShouldThrowDbException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);

    when(repo.findStatusById(2L))
        .thenCallRealMethod();

    DBException e = Assertions.assertThrows(DBException.class, () -> repo.findStatusById(2L));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());
  }


}
