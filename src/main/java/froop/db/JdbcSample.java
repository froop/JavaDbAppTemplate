package froop.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import froop.db.common.DerbyConnectionFactory;

public class JdbcSample {
  private static final String SQL_SELECT = "SELECT name FROM sample WHERE id=?";

  private final DerbyConnectionFactory factory = new DerbyConnectionFactory(
      "jdbc:derby:data/derby/sample");

  public Optional<String> selectNameById(String id) throws SQLException {
    try (Connection conn = factory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
      stmt.setString(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(rs.getString("name"));
        } else {
          return Optional.empty();
        }
      }
    }
  }
}
