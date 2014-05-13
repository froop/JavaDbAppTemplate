package froop.db.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Apache Derby を Embedded モード(同一プロセス内で起動)で使用する.
 * 参考: http://db.apache.org/derby/
 */
public class DerbyConnectionFactory {
  private final String connectUrl;

  static {
    final String logDir = "logs";
    new File(logDir).mkdir();
    System.setProperty("derby.stream.error.file", logDir + "/derby.log");
  }

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
