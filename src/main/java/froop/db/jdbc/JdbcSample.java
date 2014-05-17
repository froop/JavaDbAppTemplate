package froop.db.jdbc;

import froop.domain.SampleData;
import froop.domain.SampleValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBCでデータベースに直接アクセスするサンプル.
 */
public class JdbcSample implements SampleData {
  private static final String SQL_SELECT_MULTI = "SELECT id, name FROM sample ORDER BY name";
  private static final String SQL_SELECT_SINGLE = "SELECT name FROM sample WHERE id=?";
  private static final String SQL_UPDATE = "UPDATE sample SET name=? WHERE id=?";

  private final JdbcSqlExecutor executor = new JdbcSqlExecutor("jdbc:derby:data/derby/sample");

  @Override
  public List<SampleValue> queryAll() {
    return executor.query(SQL_SELECT_MULTI, stmt -> {}, rs -> {
      List<SampleValue> list = new ArrayList<>();
      while (rs.next()) {
        list.add(SampleValue.of(rs.getInt("id"), rs.getString("name")));
      }
      return list;
    });
  }

  @Override
  public Optional<String> queryNameById(int id) {
    return executor.query(SQL_SELECT_SINGLE, stmt -> {
      stmt.setInt(1, id);
    }, rs -> {
      if (rs.next()) {
        return Optional.of(rs.getString("name"));
      } else {
        return Optional.empty();
      }
    });
  }

  @Override
  public void update(int id, String name) {
    executor.update(SQL_UPDATE, stmt -> {
        stmt.setString(1, name);
        stmt.setInt(2, id);
    });
  }
}
