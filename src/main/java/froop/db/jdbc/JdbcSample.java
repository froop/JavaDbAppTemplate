package froop.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * JDBCでデータベースに直接アクセスするサンプル.
 */
public class JdbcSample {
  private static final String SQL_SELECT = "SELECT name FROM sample WHERE id=?";
  private static final String SQL_UPDATE = "UPDATE sample SET name=?  WHERE id=?";

  private final DerbyConnectionFactory factory = new DerbyConnectionFactory(
      "jdbc:derby:data/derby/sample");

  public Optional<String> selectNameById(long id) throws SQLException {
    try (Connection conn = factory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(rs.getString("name"));
        } else {
          return Optional.empty();
        }
      }
    }
  }

  public void update(long id, String name) throws SQLException {
    try (Connection conn = factory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
      stmt.setString(1, name);
      stmt.setLong(2, id);
      stmt.execute();
    }
  }
}
