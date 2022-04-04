package ch19;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
  private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
  private static final String USER = "webdb";
  private static final String PASSWD = "1234";

  public DBConnection() throws Exception {
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException ex) {
      throw new Exception("DBConnection" + ex.getMessage());
    }
  }

  public Connection getConnection() throws Exception {
    Connection conn;
    try {
      conn = DriverManager.getConnection(JDBC_URL, USER, PASSWD);
    } catch (SQLException ex) {
      throw new SQLException("DBConnection" + ex.getMessage());
    }
    return conn;
  }
}