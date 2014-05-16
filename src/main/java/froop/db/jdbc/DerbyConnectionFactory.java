package froop.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnectionFactory {
  private final String connectUrl;

  /**
   * @param connectUrl JDBC用のデータベース接続URL
   */
  public DerbyConnectionFactory(String connectUrl) {
    this.connectUrl = connectUrl;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(connectUrl);
  }
}
