package froop.db.jdbc;

import java.sql.*;

public class JdbcSqlExecutor {
  private final String dbUrl;

  public JdbcSqlExecutor(String dbUrl) {
    this.dbUrl = dbUrl;
  }

  public <R> R query(String sqlString, StatementSetter setter, ResultGetter<R> getter) {
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

  public void update(String sqlString, StatementSetter setter) {
    execute(conn -> {
      try (PreparedStatement statement = conn.prepareStatement(sqlString)) {
        setter.accept(statement);
        statement.execute();
        return null;
      }
    });
  }

  @FunctionalInterface
  public static interface StatementSetter {
    void accept(PreparedStatement statement) throws SQLException;
  }

  @FunctionalInterface
  public static interface ResultGetter<R> {
    R apply(ResultSet resultSet) throws SQLException;
  }

  private <R> R execute(SqlExecutor<R> executor) {
    try (Connection conn = DriverManager.getConnection(dbUrl)) {
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
