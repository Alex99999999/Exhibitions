package com.my.utils;

import static org.mockito.Mockito.mock;

import com.my.db.connection.MySqlConnection;
import java.sql.Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class DbUtilsTest {

  private static MockedStatic<DbUtils> mockDbUtils;
  private static MySqlConnection mySqlConnection;
  private static Connection con;

  @BeforeAll
  static void setup() {
    mockDbUtils = Mockito.mockStatic(DbUtils.class);
    mySqlConnection = mock(MySqlConnection.class);
    con = mock (Connection.class);
  }


  @AfterAll
  static void tearDown() {
    mockDbUtils.close();
  }


  @ParameterizedTest
  @ValueSource(strings = {"!", "%", "_", "["})
  void escapeSymbolsForPstmtShouldReturnEscapedSymbol(String input) {
    mockDbUtils.when(() -> DbUtils.escapeForPstmt(input)).thenCallRealMethod();

    Assertions.assertTrue(DbUtils.escapeForPstmt(input).contains("!"));

  }
}
