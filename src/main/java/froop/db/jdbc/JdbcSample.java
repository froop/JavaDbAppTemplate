package froop.db.jdbc;

import froop.domain.SampleData;
import froop.domain.SampleValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBCでデータベースに直接アクセスするサンプル.
 */
public class JdbcSample implements SampleData {
  private static final String SQL_SELECT_MULTI = "SELECT id, name FROM sample ORDER BY name";
  private static final String SQL_SELECT_SINGLE = "SELECT id, name FROM sample WHERE id=?";
  private static final String SQL_UPDATE = "UPDATE sample SET name=? WHERE id=?";

  private final JdbcSqlExecutor executor = new JdbcSqlExecutor("jdbc:derby:data/derby/sample");

  @Override
  public List<SampleValue> queryAll() {
    return executor.query(SQL_SELECT_MULTI, stmt -> {}, rs -> {
      List<SampleValue> list = new ArrayList<>();
      while (rs.next()) {
        list.add(toValue(rs));
      }
      return list;
    });
  }

  @Override
  public Optional<SampleValue> queryById(int id) {
    return executor.query(SQL_SELECT_SINGLE, stmt -> {
      int idx = 1;
      stmt.setInt(idx++, id);
    }, rs -> {
      if (rs.next()) {
        return Optional.of(toValue(rs));
      } else {
        return Optional.empty();
      }
    });
  }

  @Override
  public void update(SampleValue value) {
    executor.update(SQL_UPDATE, stmt -> {
      int idx = 1;
      stmt.setString(idx++, value.getName());
      stmt.setInt(idx++, value.getId());
    });
  }

  private SampleValue toValue(ResultSet rs) throws SQLException {
    return SampleValue.of(rs.getInt("id"), rs.getString("name"));
  }
}
