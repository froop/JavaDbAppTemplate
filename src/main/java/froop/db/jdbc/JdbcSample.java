package froop.db.jdbc;

import froop.domain.SampleData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBCでデータベースに直接アクセスするサンプル.
 */
public class JdbcSample implements SampleData {
  private static final String DB_URL = "jdbc:derby:data/derby/sample";
  private static final String SQL_SELECT_MULTI = "SELECT name FROM sample ORDER BY name";
  private static final String SQL_SELECT_SINGLE = "SELECT name FROM sample WHERE id=?";
  private static final String SQL_UPDATE = "UPDATE sample SET name=? WHERE id=?";

  @Override
  public List<String> queryAll() {
    return executeQuery(SQL_SELECT_MULTI, stmt -> {}, rs -> {
      List<String> list = new ArrayList<>();
      while (rs.next()) {
        list.add(rs.getString("name"));
      }
      return list;
    });
  }

  @Override
  public Optional<String> queryNameById(long id) {
    return executeQuery(SQL_SELECT_SINGLE, stmt -> {
      stmt.setLong(1, id);
    }, rs -> {
      if (rs.next()) {
        return Optional.of(rs.getString("name"));
      } else {
        return Optional.empty();
      }
    });
  }

  @Override
  public void update(long id, String name) {
    executeUpdate(SQL_UPDATE, stmt -> {
        stmt.setString(1, name);
        stmt.setLong(2, id);
    });
  }

  private <R> R executeQuery(String sqlString, StatementSetter setter, ResultGetter<R> getter) {
    return execute(conn -> {
      try (PreparedStatement statement = conn.prepareStatement(sqlString)) {
        setter.accept(statement);
        statement.execute();
        try (ResultSet resultSet = statement.executeQuery()) {
          return getter.apply(resultSet);
        }
      }
    });
  }

  private void executeUpdate(String sqlString, StatementSetter setter) {
    execute(conn -> {
      try (PreparedStatement statement = conn.prepareStatement(sqlString)) {
        setter.accept(statement);
        statement.execute();
        return null;
      }
    });
  }

  @FunctionalInterface
  private static interface StatementSetter {
    void accept(PreparedStatement statement) throws SQLException;
  }

  @FunctionalInterface
  private static interface ResultGetter<R> {
    R apply(ResultSet resultSet) throws SQLException;
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
