package froop.db.jdbc;

import froop.domain.SampleData;

import java.sql.*;
import java.util.Optional;

/**
 * JDBCでデータベースに直接アクセスするサンプル.
 */
public class JdbcSample implements SampleData {
  private static final String DB_URL = "jdbc:derby:data/derby/sample";
  private static final String SQL_SELECT = "SELECT name FROM sample WHERE id=?";
  private static final String SQL_UPDATE = "UPDATE sample SET name=? WHERE id=?";

  @Override
  public Optional<String> queryNameById(long id) {
    return execute(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT)) {
        stmt.setLong(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            return Optional.of(rs.getString("name"));
          } else {
            return Optional.empty();
          }
        }
      }
    });
  }

  @Override
  public void update(long id, String name) {
    executeUpdate(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
        stmt.setString(1, name);
        stmt.setLong(2, id);
        stmt.execute();
      }
    });
  }

  private void executeUpdate(UpdateSqlExecutor executor) {
    execute(conn -> {
      executor.execute(conn);
      return null;
    });
  }

  @FunctionalInterface
  private static interface UpdateSqlExecutor {
    void execute(Connection conn) throws SQLException;
  }

  private <R> R execute(SqlExecutor<R> executor) {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
      return executor.execute(conn);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @FunctionalInterface
  private static interface SqlExecutor<R> {
    R execute(Connection conn) throws SQLException;
  }
}
