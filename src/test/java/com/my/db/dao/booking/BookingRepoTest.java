package com.my.db.dao.booking;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.my.db.dao.bookingStatus.BookingStatusDao;
import com.my.db.dao.exhibition.ExhibitionDao;
import com.my.db.dao.user.UserDao;
import com.my.entity.Booking;
import com.my.entity.BookingStatus;
import com.my.entity.Exhibition;
import com.my.entity.User;
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

public class BookingRepoTest {

  private static final long ID = 200000;
  private ResultSet rs;
  private Statement stmt;
  private PreparedStatement ps;
  private Connection con;
  private BookingRepo repo;
  private UserDao userDao;
  private BookingStatusDao bookingStatusDao;
  private ExhibitionDao exhibitionDao;

  private static MockedStatic<BookingRepo> mockRepo;
  private static MockedStatic<DbUtils> mockDbUtils;
  private static MockedStatic<UserDao> mockUserDao;
  private static MockedStatic<BookingStatusDao> mockBookingStatusDao;
  private static MockedStatic<ExhibitionDao> mockExhibitionDao;

  @BeforeClass
  public static void setupGlobal() {
    mockRepo = mockStatic(BookingRepo.class);
    mockDbUtils = mockStatic(DbUtils.class);
    mockUserDao = mockStatic(UserDao.class);
    mockBookingStatusDao = mockStatic(BookingStatusDao.class);
    mockExhibitionDao = mockStatic(ExhibitionDao.class);
  }

  @AfterClass
  public static void tearDownGlobal() {
    mockRepo.close();
    mockDbUtils.close();
    mockUserDao.close();
    mockBookingStatusDao.close();
    mockExhibitionDao.close();
  }

  @Before
  public void setUp() throws SQLException, DBException {
    rs = mock(ResultSet.class);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(150L)
        .thenReturn(180L);

    when(rs.getInt(Columns.TICKET_QTY))
        .thenReturn(1)
        .thenReturn(1);

    stmt = mock(Statement.class);

    ps = mock(PreparedStatement.class);
    when(ps.executeQuery())
        .thenReturn(rs);

    con = mock(Connection.class);

    repo = mock(BookingRepo.class);
    userDao = mock(UserDao.class);
    bookingStatusDao = mock(BookingStatusDao.class);
    exhibitionDao = mock(ExhibitionDao.class);

    mockRepo.when(BookingRepo::getInstance).thenReturn(repo);
    mockDbUtils.when(DbUtils::getCon).thenReturn(con);
    mockUserDao.when(UserDao::getInstance).thenReturn(userDao);
    mockBookingStatusDao.when(BookingStatusDao::getInstance).thenReturn(bookingStatusDao);
    mockExhibitionDao.when(ExhibitionDao::getInstance).thenReturn(exhibitionDao);

    when(userDao.findById(20L)).thenReturn(User.getInstance());
    when(exhibitionDao.findById(20L)).thenReturn(Exhibition.getInstance());
    when(bookingStatusDao.findById(20L)).thenReturn(BookingStatus.getInstance());

  }

  @Test
  public void findAllBookingsShouldReturnList() throws SQLException, DBException {
    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    when(repo.findAllBookings())
        .thenCallRealMethod();

    List<Booking> list = repo.findAllBookings();

    Assertions.assertEquals(2, list.size());
  }

  @Test
  public void findAllBookingsShouldThrowDbException() throws SQLException, DBException {
    reset(rs);

    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.FIND_ALL))
        .thenReturn(rs);

    when(repo.findAllBookings())
        .thenCallRealMethod();

    DBException e = Assertions.assertThrows(DBException.class, () -> repo.findAllBookings());
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());

  }


  @Test
  public void findBookingByIdShouldReturnBooking() throws SQLException, DBException {
    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);

    when(repo.findBookingById(150L))
        .thenCallRealMethod();

    Booking booking = repo.findBookingById(150L);

    Assertions.assertNotNull(booking);
    Assertions.assertEquals(150, booking.getId());
  }

  @Test
  public void findBookingByIdShouldReturnNull() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(false);

    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);

    when(repo.findBookingById(ID))
        .thenCallRealMethod();

    Booking booking = repo.findBookingById(ID);

    Assertions.assertNull(booking);

  }

  @Test
  public void findBookingByIdShouldThrowDbException() throws SQLException, DBException {
    reset(rs);

    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.prepareStatement(Sql.FIND_BY_ID))
        .thenReturn(ps);

    when(repo.findBookingById(ID))
        .thenCallRealMethod();

    DBException e = Assertions.assertThrows(DBException.class, () -> repo.findBookingById(ID));
    Assertions.assertEquals(Logs.NOTHING_FOUND_PER_YOUR_REQUEST, e.getMessage());

  }

  @Test
  public void getByUserIdShouldReturnList() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getLong(Columns.ID))
        .thenReturn(150L)
        .thenReturn(150L)
        .thenReturn(150L);

    when(rs.getInt(Columns.TICKET_QTY))
        .thenReturn(1)
        .thenReturn(3)
        .thenReturn(1);

    when(con.prepareStatement(Sql.FIND_BY_USER_ID))
        .thenReturn(ps);

    when(repo.getByUserId(150L))
        .thenCallRealMethod();

    BookingRepo repo = BookingRepo.getInstance();

    List<Booking> list = repo.getByUserId(150L);

    Assertions.assertEquals(3, list.size());

  }

  @Test
  public void findAllBookingCountShouldReturnCount() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getInt(1))
        .thenReturn(2);

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.GET_ALL_BOOKING_COUNT))
        .thenReturn(rs);

    when(repo.findAllBookingCount())
        .thenCallRealMethod();

    int count = repo.findAllBookingCount();

    Assertions.assertEquals(2, count);

  }


  @Test
  public void findAllBookingCountShouldThrowDbException() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.createStatement())
        .thenReturn(stmt);

    when(stmt.executeQuery(Sql.GET_ALL_BOOKING_COUNT))
        .thenReturn(rs);

    when(repo.findAllBookingCount())
        .thenCallRealMethod();

    DBException ex = Assertions.assertThrows(DBException.class, () -> repo.findAllBookingCount());
    Assertions.assertEquals(Logs.UNABLE_TO_GET_COUNT, ex.getMessage());

  }

  @Test
  public void countByExhibitionIdAndStatusReturnCount() throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenReturn(true)
        .thenReturn(false);

    when(rs.getInt(1))
        .thenReturn(2);

    when(con.prepareStatement(Sql.FIND_BY_EXHIBITION_ID_AND_BOOKING_STATUS))
        .thenReturn(ps);

    when(repo.countByExhibitionIdAndStatus(ID, "booked", "paid"))
        .thenCallRealMethod();

    int count = repo.countByExhibitionIdAndStatus(ID, "booked", "paid");

    Assertions.assertEquals(2, count);

  }


  @Test
  public void countByExhibitionIdAndStatusShouldThrowDbException()
      throws SQLException, DBException {
    reset(rs);
    when(rs.next())
        .thenThrow(SQLException.class);

    when(con.createStatement())
        .thenReturn(stmt);

    when(con.prepareStatement(Sql.FIND_BY_EXHIBITION_ID_AND_BOOKING_STATUS))
        .thenReturn(ps);

    when(repo.countByExhibitionIdAndStatus(ID, "booked", "paid"))
        .thenCallRealMethod();

    DBException ex = Assertions.assertThrows(DBException.class,
        () -> repo.countByExhibitionIdAndStatus(ID, "booked", "paid"));
    Assertions.assertEquals(Logs.UNABLE_TO_GET_COUNT, ex.getMessage());

  }

  @Test
  public void FilterAllByStringLimitOffsetShouldReturnList() throws SQLException, DBException {
    String sql = Sql.FIND_ALL_BY_STATUS_OFFSET_LIMIT_NO_FILTER;
    when(con.prepareStatement(sql))
        .thenReturn(ps);

    when(repo.filterAllByStringLimitOffset(sql, "filter", 2, 0))
        .thenCallRealMethod();

    List<Booking> list = repo.filterAllByStringLimitOffset(sql, "filter", 2, 0);

    Assertions.assertEquals(2, list.size());
  }

  @Test
  public void filterAllByStatusAndLoginLimitOffsetShouldReturnList()
      throws SQLException, DBException {
    String sql = Sql.FIND_ALL_BY_LOGIN_AND_STATUS_OFFSET_LIMIT_NO_FILTER;
    when(con.prepareStatement(sql))
        .thenReturn(ps);

    when(repo.filterAllByStatusAndLoginLimitOffset(sql, "login", "filter", 2, 0))
        .thenCallRealMethod();

    List<Booking> list = repo.filterAllByStatusAndLoginLimitOffset(sql, "login", "filter", 2, 0);

    Assertions.assertEquals(2, list.size());
  }

  @Test
  public void findAllLimitOffsetShouldReturnList()
      throws SQLException, DBException {
    String sql = Sql.FIND_ALL_OFFSET_LIMIT;
    when(con.prepareStatement(sql))
        .thenReturn(ps);

    when(repo.findAllLimitOffset(sql, 2, 0))
        .thenCallRealMethod();

    List<Booking> list = repo.findAllLimitOffset(sql, 2, 0);

    Assertions.assertEquals(2, list.size());
  }

}
