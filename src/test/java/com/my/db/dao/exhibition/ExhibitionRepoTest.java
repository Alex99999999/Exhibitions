package com.my.db.dao.exhibition;

import static org.mockito.Mockito.mock;

public class ExhibitionRepoTest {

//  private static MockedStatic<DbUtils> utils;
//  private static MockedStatic<ExhibitionRepo> mockRepo;
//  private static MockedStatic<ExhibitionStatusDao> mockStatusDao;
//  private static MockedStatic<CurrencyDao> mockCurrencyDao;
//  private static MockedStatic<ExhibitionService> mockService;
//
//  private ExhibitionRepo repo;
//  ExhibitionStatusDao statusDao;
//  CurrencyDao curDao;
//  private Statement stmt;
//  private PreparedStatement ps;
//  private Connection con;
//  private ResultSet rs;
//  private ExhibitionService service;
//
//
//  @BeforeClass
//  public static void setupGlobal() {
//    utils = Mockito.mockStatic(DbUtils.class);
//    mockRepo = Mockito.mockStatic(ExhibitionRepo.class);
//    mockStatusDao = Mockito.mockStatic(ExhibitionStatusDao.class);
//    mockCurrencyDao = Mockito.mockStatic(CurrencyDao.class);
//    mockService = Mockito.mockStatic(ExhibitionService.class);
//  }
//
//  @AfterClass
//  public static void tearDownGlobal() {
//    utils.close();
//    mockRepo.close();
//    mockStatusDao.close();
//    mockCurrencyDao.close();
//    mockService.close();
//  }
//
//  @Before
//  public void setup() throws SQLException, DBException, ValidationException {
//    repo = mock(ExhibitionRepo.class);
//    stmt = mock(Statement.class);
//    ps = mock(PreparedStatement.class);
//    con = mock(Connection.class);
//    rs = mock(ResultSet.class);
//    statusDao = mock(ExhibitionStatusDao.class);
//    curDao = mock(CurrencyDao.class);
//    service = mock(ExhibitionService.class);
//
//    utils.when(DbUtils::getCon).thenReturn(con);
//    mockRepo.when(ExhibitionRepo::getInstance).thenReturn(repo);
//    mockStatusDao.when(ExhibitionStatusDao::getInstance).thenReturn(statusDao);
//    mockCurrencyDao.when(CurrencyDao::getInstance).thenReturn(curDao);
//    mockService.when(ExhibitionService::getInstance).thenReturn(service);
//
//    when(rs.next())
//        .thenReturn(true)
//        .thenReturn(true)
//        .thenReturn(false);
//
//    when(rs.getLong(Columns.ID))
//        .thenReturn(25L)
//        .thenReturn(50L);
//
//    when(rs.getString(Columns.ID))
//        .thenReturn("Topic1")
//        .thenReturn("Topic2");
//
//    when(ps.executeQuery())
//        .thenReturn(rs);
//
//    when(statusDao.findById(50L)).thenReturn(ExhibitionStatus.getInstance());
//    when(curDao.findById(50L)).thenReturn((Currency.getInstance()));
//    when(service.dateDisplayFormat("2021-12-12"))
//        .thenCallRealMethod();
//    when(service.timeDisplayFormat("08:00:00"))
//        .thenCallRealMethod();
//  }

//  @Test
//  public void findAllExhibitionsShouldReturnList() throws SQLException, DBException {
//    when(con.createStatement())
//        .thenReturn(stmt);
//    when(stmt.executeQuery(Sql.FIND_ALL))
//        .thenReturn(rs);
//    when(repo.findAllExhibitions())
//        .thenCallRealMethod();
//    List<Exhibition> list = repo.findAllExhibitions();
//    Assertions.assertEquals(2, list.size());
//
//
//  }

}
